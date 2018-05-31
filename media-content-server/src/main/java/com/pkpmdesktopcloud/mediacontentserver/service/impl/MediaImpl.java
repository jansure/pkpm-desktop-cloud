package com.pkpmdesktopcloud.mediacontentserver.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.desktop.constant.FileServerConstant;
import com.desktop.constant.FileTypeEnum;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.page.ResultObject;
import com.google.common.io.Files;
import com.pkpm.pdfconverterutil.office.OfficeDocConverter;
import com.pkpm.pdfconverterutil.office.OfficeDocConverterFactory;
import com.pkpm.pdfconverterutil.services.OpenOfficeService;
import com.pkpmdesktopcloud.mediacontentserver.dao.PkpmFileConfigDao;
import com.pkpmdesktopcloud.mediacontentserver.dao.PkpmFileInfoDao;
import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;
import com.pkpmdesktopcloud.mediacontentserver.service.IMediaService;
import com.pkpmdesktopcloud.mediacontentserver.vo.StorePath;
import com.pkpmdesktopcloud.redis.RedisCache;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MediaImpl implements IMediaService {
	
	@Resource
	private PkpmFileInfoDao fileInfoDao;

	@Resource
	private PkpmFileConfigDao fileConfigDao;
	
	@Value("${nginx.url}")
	private String url;
	
	@Value("${upload.url}")
	private String uploadUrl;
	
	  
	/* (非 Javadoc)  
	 *   
	 *     
	 * @see com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#convertPdf()  
	 */  
	    
	@Override
	public void convertPdf() {
		Map<byte[], byte[]> allData = getRedisFileByType(FileTypeEnum.DATUM.toString());
		if(allData == null || allData.size() == 0) {
			return;
		}
		
		try {
    		
			//开启转换服务
    		List<Long> listenInPorts = new ArrayList<>(); 
            listenInPorts.add(48100L);
            OpenOfficeService.initialize(listenInPorts);
            OpenOfficeService.start();
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}

		for(byte[] key : allData.keySet()) {
			
			String groupAndPath = new String(key);
			log.info("groupAndPath:{}", groupAndPath);
			if(groupAndPath.endsWith(".pdf")) {//pdf文件排除
				continue;
			}
			
			String fileName = groupAndPath.substring(groupAndPath.lastIndexOf("/"));
			File tempDir = Files.createTempDir();
			
			//下载
			String downFile = tempDir.getAbsolutePath() + fileName;
			download(url + groupAndPath, downFile);
			String dst = null;
			
			try {
				
				OfficeDocConverter officeDocumentConverter = OfficeDocConverterFactory.getConverter(downFile);
				
				//从数据库中查询上传信息
				PkpmFileInfo fileInfo = getPkpmFileInfo(groupAndPath);
				fileName = getOriginFileName(fileInfo);
				
				dst = tempDir.getAbsolutePath() + "/" + fileName + ".pdf";
		        log.info("dst:{}", dst);
		        
		        //转换pdf
		        officeDocumentConverter.toPdf(dst);
		        
			}catch (Exception ex) {
				ex.printStackTrace();
				log.info("转换出错，文件名：{}", groupAndPath);
			}
			
			//上传
	        String response = uploadFile(uploadUrl, dst);
	        String fullPath = getFullPath(response);
	        
	        //删除缓存
	        removeRedisKey(FileTypeEnum.DATUM.toString(), groupAndPath);
		}
		
		//关闭转换服务
		OpenOfficeService.stop();
	}

	/**  
	 * 删除缓存数据
	 */  
	private void removeRedisKey(String id, String key) {
		RedisCache cache = new RedisCache(id);
		cache.removeObject(key);
	}

	/**  
	 * @Title: getOriginFileName  
	 * @Description: 获取原始文件名（没有扩展名）
	 * @param @param fileInfo
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws  
	 */  
	private String getOriginFileName(PkpmFileInfo fileInfo) {
		if(fileInfo == null) {
			return "NoName";
		}
		
		String name = fileInfo.getOriginFileName();
		if(name.indexOf(".") > -1) {
			String postFix = fileInfo.getPostFix();
			int length = name.length() - postFix.length();
			name = name.substring(0, length - 1);
		}
		
		return name;
	}



	/**  
	 * @Title: getPkpmFileInfo  
	 * @Description: 从数据库中查询上传信息
	 * @param @param groupAndPath 文件组名和真实路径
	 * @return PkpmFileInfo    返回文件信息
	 */  
	private PkpmFileInfo getPkpmFileInfo(String groupAndPath) {
		
		//获取组Id和真实路径
		int index = groupAndPath.indexOf("/");
		String groupNameStr = groupAndPath.substring(5, index);
		String destFileName = groupAndPath.substring(index + 1);
		
		//设置查询条件，检索数据库中数据
		PkpmFileInfo fileInfo = new PkpmFileInfo();
		fileInfo.setGroupName(new Integer(groupNameStr));
		fileInfo.setDestFileName(destFileName);
		
		PkpmFileInfo result = fileInfoDao.selectOne(fileInfo );
		return result;
	}


	/* (非 Javadoc)  
	 *   
	 *     
	 * @see com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#captureByPdf()  
	 */  
	    
	@Override
	public void captureByPdf() {
		Map<byte[], byte[]> allData = getRedisFileByType(FileTypeEnum.DATUM.toString());
		if(allData == null || allData.size() == 0) {
			return;
		}
		
		for(byte[] key : allData.keySet()) {
			
			String groupAndPath = new String(key);
			
			if(groupAndPath.endsWith(".pdf")) {//只处理pdf文件
				captureByPdf(groupAndPath);
			}
			
		}
	}
	
	/**  
	 * @Title: captureByPdf  
	 * @Description: 截取pdf文件的缩略图 
	 * @param groupAndPath 文件真实路径
	 * @throws  
	 */  
	private void captureByPdf(String groupAndPath) {
		String fileName = groupAndPath.substring(groupAndPath.lastIndexOf("/"));
		File tempDir = Files.createTempDir();
		
		//下载
		String downFile = tempDir.getAbsolutePath() + fileName;
		download(url + groupAndPath, downFile);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *     
	 * @see com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#captureByVideo()  
	 */  
	@Override
	public void captureByVideo() {
		// TODO Auto-generated method stub
		
	}
	
	private Map<byte[], byte[]> getRedisFileByType(String fileType) {
		RedisCache cache = null;
		
		switch(FileTypeEnum.eval(fileType)) {
			case IMAGE :
				cache = new RedisCache(FileServerConstant.FILE_SERVER_UPLOAD_IMAGE_KEY);
				break;
			case VIDEO :
				cache = new RedisCache(FileServerConstant.FILE_SERVER_UPLOAD_VIDEO_KEY);
				break;
			case DATUM :
				cache = new RedisCache(FileServerConstant.FILE_SERVER_UPLOAD_DATUM_KEY);
				break;
			default :
				log.error("文件类型错误：{}", fileType);
		}
		
		return cache.getAll();
	}

	/**
	 * 
	 * @Title: download  
	 * @Description: 下载文件
	 * @param @param downUrl url
	 * @param @param downPath 文件路径
	 * @throws
	 */
	private void download(String downUrl, String downPath) {
        
        int byteread = 0;
        
        try {
        	URL url = new URL(downUrl);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(downPath);

            byte[] buffer = new byte[4096];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	public String uploadFile(String serverUrl, String localFilePath) {  
        String respStr = null;  
        
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpPost httppost = new HttpPost(serverUrl);  
            FileBody binFileBody = new FileBody(new File(localFilePath));  
  
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();  
            multipartEntityBuilder.setCharset(Consts.UTF_8);//设置请求的编码格式  
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式  
            // add the file params  
            multipartEntityBuilder.addPart("file", binFileBody);  
  
            HttpEntity reqEntity = multipartEntityBuilder.build();  
            httppost.setEntity(reqEntity);  
  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try { 
                HttpEntity resEntity = response.getEntity();  
//                respStr = getRespString(resEntity); 
                respStr = EntityUtils.toString(resEntity, "UTF-8");
                EntityUtils.consume(resEntity);  
            }catch(Exception ex) {
            	ex.printStackTrace();
            	log.error("上传文件出错，文件：{}", localFilePath);
            }finally {  
                response.close();  
            }  
        } catch(Exception ex) {
        	ex.printStackTrace();
        	log.error("上传文件出错，文件：{}", localFilePath);
        } finally {  
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
        return respStr;  
    } 
	
	/** 
     * 获取上传后的文件路径
     */  
    private String getFullPath(String response){  
		//解析返回对象
		ResultObject result = JsonUtil.deserialize(response, ResultObject.class);
		Integer code = result.getCode();
		
		//返回正常时处理
		if(code == HttpStatus.OK.value()) {
			String detailStr = (String)result.getData();
			StorePath storePath = JsonUtil.deserialize(detailStr, StorePath.class);
			return storePath.getFullPath();
		}
		
		log.error("获取上传路径出错:{}", response);
        return null;  
    }

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileInfo
	 * @return  
	 * @see com.pkpmdesktopcloud.mediacontentserver.service.IMediaService#selectFile(com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo)  
	 */  
	@Override
	public PkpmFileInfo selectFile(PkpmFileInfo fileInfo) {
		return fileInfoDao.selectOne(fileInfo);
	}  

}

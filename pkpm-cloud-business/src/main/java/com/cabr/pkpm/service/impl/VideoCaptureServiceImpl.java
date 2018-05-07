package com.cabr.pkpm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cabr.pkpm.constants.SysConstant;
import com.cabr.pkpm.entity.PkpmCloudVideoCapture;
import com.cabr.pkpm.entity.SysConfig;
import com.cabr.pkpm.mapper.PkpmCloudVideoCaptureMapper;
import com.cabr.pkpm.mapper.product.ProductMapper;
import com.cabr.pkpm.service.IVideoCaptureService;
import com.desktop.utils.FileUtil;
import com.desktop.utils.VideoCaptureUtil;
import com.gateway.common.dto.FileServerResponse;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: VideoCaptureServiceImpl
 * @Description: 视频截图Service实现类
 * @author wangxiulong
 * @date 2018年5月7日
 *
 */
@Service
@Slf4j
public class VideoCaptureServiceImpl implements IVideoCaptureService {

	/**
	 * 定义视频类型
	 */
	private String[] vidoTypeArray = new String[] { ".mp4", ".avi" };

	@Resource
	private PkpmCloudVideoCaptureMapper videoCaptureMapper;

	@Resource
	private ProductMapper productMapper;

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param record
	 * 
	 * @return
	 * 
	 * @see com.cabr.pkpm.service.IVideoCaptureService#insert(com.cabr.pkpm.entity.
	 * PkpmCloudVideoCapture)
	 */
	@Override
	public int insert(PkpmCloudVideoCapture record) {

		return videoCaptureMapper.insert(record);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param capId
	 * 
	 * @return
	 * 
	 * @see com.cabr.pkpm.service.IVideoCaptureService#selectByPrimaryKey(java.lang.
	 * Integer)
	 */
	@Override
	public PkpmCloudVideoCapture selectByPrimaryKey(Integer capId) {

		return videoCaptureMapper.selectByPrimaryKey(capId);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @return
	 * 
	 * @see com.cabr.pkpm.service.IVideoCaptureService#selectAll()
	 */
	@Override
	public List<PkpmCloudVideoCapture> selectAll() {

		return videoCaptureMapper.selectAll();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @return
	 * 
	 * @see com.cabr.pkpm.service.IVideoCaptureService#initData()
	 */
	@Override
	public boolean initData() {
		
		//判断数据库中是否有数据，有数据时不需要处理
		List<PkpmCloudVideoCapture> list = selectAll();
		if(CollectionUtils.isNotEmpty(list)) {
			log.info("视频截图数据已存在，不需要初始化操作。");
			return false;
		}
		
		// 获取文件系统路径
		SysConfig sysConfig = productMapper.getSysConfig(SysConstant.FILE_BASE_URL);
		Preconditions.checkNotNull(sysConfig, "文件系统配置为空。");
		String fileUrl = sysConfig.getValue();
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileUrl), "文件URL为空。");
				
		//获取所有视频文件
		List<String> fileList = getAllVideoFile(fileUrl);
		
		if(CollectionUtils.isNotEmpty(fileList)) {
			
			for(String file : fileList) {
				
				//将视频文件截图
				byte[] data = VideoCaptureUtil.fetchFrameByJavacv(fileUrl + "/" + file);
				
				//保存数据库
				if(data != null) {
					PkpmCloudVideoCapture videoCapture = new PkpmCloudVideoCapture();
					videoCapture.setCapValue(data);
					videoCapture.setCapType("jpg");
					insert(videoCapture);
				}
				
			}
			
		}
		
		return true;
	}

	/**
	 * 
	 * @Title: getAllVideoFile  
	 * @Description: 获取所有视频文件
	 * @param fileUrl   文件系统路径
	 * @return List<String>    返回视频文件集合
	 * @throws  
	 */  
	private List<String> getAllVideoFile(String fileUrl) {
		
		List<String> fileList = new ArrayList<String>();
		String json = FileUtil.loadJson(fileUrl);
		Preconditions.checkArgument(StringUtils.isNotEmpty(json), "文件服务器返回数据为空。");
		
		 try {
			FileServerResponse fileServerResponse = JsonUtil.deserialize(json, FileServerResponse.class);
	        if (fileServerResponse != null && fileServerResponse.getFiles() != null) {
	            
	        	// 遍历已有的文件列表，查找视频文件
	        	for(String fileName : fileServerResponse.getFiles()) {
	        		
	        		//获取文件类型
	        		String typeStr = FileUtil.getFileType(fileName);
	        		for(String vidoType : vidoTypeArray) {
	        			if(typeStr.equalsIgnoreCase(vidoType)) {//找到视频文件
	        				fileList.add(fileName);
	        				break;
	        			}
	        		}
	        	}
	        }
            
		 } catch (Exception e) {
			 
             log.error(e.getMessage());

         }

		 return fileList;
	}

}

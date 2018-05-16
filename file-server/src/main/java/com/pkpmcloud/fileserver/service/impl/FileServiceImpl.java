package com.pkpmcloud.fileserver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.desktop.utils.StringOrDate;
import com.desktop.utils.TimeConverterUtil;
import com.pkpmcloud.fileserver.VO.PkpmFileInfoVO;
import com.pkpmcloud.fileserver.dao.PkpmFileInfoDao;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;
import com.pkpmcloud.fileserver.service.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	@Resource
	private PkpmFileInfoDao fileInfoDao;

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param md5
	 * @return  
	 * @see com.pkpmcloud.fileserver.service.IFileService#selectByMd5(java.lang.String)  
	 */  
	@Override
	public PkpmFileInfo selectByMd5(String md5) {
		return fileInfoDao.selectByMd5(md5);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileInfo
	 * @return  
	 * @see com.pkpmcloud.fileserver.service.IFileService#insert(com.pkpmcloud.fileserver.domain.PkpmFileInfo)  
	 */  
	@Override
	public Integer insert(PkpmFileInfo fileInfo) {
		return fileInfoDao.insert(fileInfo);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileInfo
	 * @return  
	 * @see com.pkpmcloud.fileserver.service.IFileService#update(com.pkpmcloud.fileserver.domain.PkpmFileInfo)  
	 */  
	@Override
	public Integer update(PkpmFileInfo fileInfo) {
		return fileInfoDao.update(fileInfo);
	}


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param multipartFile
	 * @return  
	 * @see com.pkpmcloud.fileserver.service.IFileService#getPkpmFileInfo(org.springframework.web.multipart.MultipartFile)  
	 */  
	@Override
	public PkpmFileInfo getPkpmFileInfo(MultipartFile multipartFile) {
		if(multipartFile != null && !multipartFile.isEmpty()) {
			String fileName = multipartFile.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			PkpmFileInfo fileInfo = new PkpmFileInfo();
	    	fileInfo.setCreateTime(LocalDateTime.now());
	    	fileInfo.setFileSize(getSizeStr(multipartFile.getSize()));
	    	fileInfo.setOriginFileName(fileName);
	    	fileInfo.setPostfix(ext);
	    	return fileInfo;
		}
		
		return null;
	}
	
	private String getSizeStr(long size) {
		
		//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        return String.valueOf(size) + "B";  
	    } else {  
	        size = size / 1024;  
	    }  
	    
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {  
	        return String.valueOf(size) + "KB";  
	    } else {  
	        size = size / 1024;  
	    }  
	    
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "MB";  
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "GB";  
	    }  
	}


	@SuppressWarnings("unused")
	public List<PkpmFileInfoVO> fileListByName(String fileName) {
		
		List<PkpmFileInfo> pkpmFileInfoList = fileInfoDao.fileListByName(fileName);
		
		List<PkpmFileInfoVO> showFileInfoVOList = showFileInfoVOList(pkpmFileInfoList);
		
		
		return showFileInfoVOList;
		//return pkpmFileInfoList;
	}
			
	public List<PkpmFileInfoVO> fileList() {
			
			List<PkpmFileInfo> pkpmFileInfoList = fileInfoDao.fileList();
			
			List<PkpmFileInfoVO> showFileInfoVOList = showFileInfoVOList(pkpmFileInfoList);
			
			return showFileInfoVOList;
			//return pkpmFileInfoList;
	}
    
    /**
     *  把查询出来的列表转换成前端需要的包装类型
     * @param pkpmFileInfoList
     * @return
     */
	private List<PkpmFileInfoVO> showFileInfoVOList(List<PkpmFileInfo> pkpmFileInfoList) {
		List<PkpmFileInfoVO> pkpmFileInfoVoList = new ArrayList<>();
		for (PkpmFileInfo fileInfo : pkpmFileInfoList) {
			
			PkpmFileInfoVO pkpmFileInfoVO = new PkpmFileInfoVO();
			LocalDateTime createTime = fileInfo.getCreateTime();
			String dateToString = TimeConverterUtil.dateToString(createTime, "yyyy年MM月dd日  HH:mm:ss");
			
			pkpmFileInfoVO.setCreateTime(dateToString); 
			pkpmFileInfoVO.setFileSize(fileInfo.getFileSize());
			pkpmFileInfoVO.setGroupName(fileInfo.getGroupName());
			pkpmFileInfoVO.setOriginFileName(fileInfo.getOriginFileName());
			pkpmFileInfoVO.setDestFileName(fileInfo.getDestFileName());
			
			pkpmFileInfoVoList.add(pkpmFileInfoVO);
		}
		return pkpmFileInfoVoList;
	}


	@Override
	public PkpmFileInfo selectFile(PkpmFileInfo fileInfo) {
		
		PkpmFileInfo pkpmFileInfo = fileInfoDao.select(fileInfo);
		return pkpmFileInfo;
	}




	
	
}

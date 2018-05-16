package com.pkpmcloud.fileserver.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pkpmcloud.fileserver.VO.PkpmFileInfoVO;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

public interface IFileService {
	
	/**
	 * 根据md5查询文件信息
	 */
	PkpmFileInfo selectByMd5(String md5);
    
	/**
	 * 插入文件信息
	 */
    Integer insert(PkpmFileInfo fileInfo);

    /**
	 * 更新文件信息
	 */
    Integer update(PkpmFileInfo fileInfo);

	  
	/**  
	 * @Title: getPkpmFileInfo  
	 * @Description: 根据上传文件生成文件实体类
	 * @param @param multipartFile
	 * @param @return    参数  
	 * @return PkpmFileInfo    返回类型  
	 * @throws  
	 */  
    
	PkpmFileInfo getPkpmFileInfo(MultipartFile multipartFile);

	List<PkpmFileInfoVO> fileListByName(String fileName);

	List<PkpmFileInfoVO> fileList();

	PkpmFileInfo selectFile(PkpmFileInfo fileInfo);


	

}

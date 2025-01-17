package com.pkpmcloud.fileserver.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.desktop.utils.page.Page;
import com.pkpmcloud.fileserver.VO.PkpmFileInfoVO;
import com.pkpmcloud.fileserver.domain.PkpmFileConfig;
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

	  
	/**  
	 * @Title: filePageListByName  
	 * @Description: 获取文件分页列表
	 * @param @param fileName 文件名
	 * @param @param beginPos 记录起始
	 * @param @param pageSize 记录条数
	 * @return Page<PkpmFileInfoVO>    返回分页信息
	 */  
	Page<PkpmFileInfoVO> filePageListByName(String fileName, Integer beginPos, Integer pageSize);

	
	/**
	 * 
	 * @Title: selectAllFileConfig  
	 * @Description: 获取所有文件配置信息
	 * @return List<PkpmFileConfig>    返回文件配置信息集合
	 * @throws
	 */
	public List<PkpmFileConfig> selectAllFileConfig();
}

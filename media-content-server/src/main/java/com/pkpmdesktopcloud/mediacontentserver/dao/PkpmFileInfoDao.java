package com.pkpmdesktopcloud.mediacontentserver.dao;

import java.util.List;

import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;

public interface PkpmFileInfoDao {
	
	PkpmFileInfo selectByMd5(String md5);
    
    Integer insert(PkpmFileInfo fileInfo);

    Integer update(PkpmFileInfo fileInfo);

	List<PkpmFileInfo> fileListByName(String fileName);

	List<PkpmFileInfo> fileList();

	PkpmFileInfo select(PkpmFileInfo fileInfo);

	  
	/**  
	 * @Title: countByName  
	 * @Description: 获取总条数
	 * @param @param fileName 文件名
	 * @return long    总条数
	 */  
	long countByName(String fileName);

	  
	/**  
	 * @Title: filePageListByName  
	 * @Description: 获取文件分页列表  
	 * @param @param fileName 文件名
	 * @param @param beginPos 开始
	 * @param @param pageSize 条数
	 * @return List<PkpmFileInfo>    文件分页列表  
	 * @throws  
	 */  
	List<PkpmFileInfo> filePageListByName(String fileName, Integer beginPos, Integer pageSize);

}

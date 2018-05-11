package com.pkpmcloud.fileserver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.pkpmcloud.fileserver.dao.PkpmFileInfoDao;
import com.pkpmcloud.fileserver.dao.mapper.PkpmFileInfoMapper;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

@Repository
public class PkpmFileInfoDaoImpl implements PkpmFileInfoDao{

	@Resource
	private PkpmFileInfoMapper fileInfoMapper;
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param md5
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileInfoDao#selectByMd5(java.lang.String)  
	 */  
	@Override
	public PkpmFileInfo selectByMd5(String md5) {
		
		PkpmFileInfo fileInfo = new PkpmFileInfo();
		fileInfo.setMd5(md5);
		
		List<PkpmFileInfo> list = fileInfoMapper.select(fileInfo );
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		
		return null;
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileInfo
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileInfoDao#insert(com.pkpmcloud.fileserver.domain.PkpmFileInfo)  
	 */  
	@Override
	public Integer insert(PkpmFileInfo fileInfo) {
		return fileInfoMapper.insert(fileInfo);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileInfo
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileInfoDao#update(com.pkpmcloud.fileserver.domain.PkpmFileInfo)  
	 */  
	@Override
	public Integer update(PkpmFileInfo fileInfo) {
		return fileInfoMapper.update(fileInfo);
	}
     
	
}

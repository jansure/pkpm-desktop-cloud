package com.pkpmcloud.fileserver.dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.desktop.utils.StringOrDate;
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
		if( list!= null && list.size() > 0) {
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


	@Override
	public List<PkpmFileInfo> fileListByName(String fileName) {
		
		List<PkpmFileInfo> list = fileInfoMapper.fileListByName(fileName);
		
		return list;
	}


	@Override
	public List<PkpmFileInfo> fileList() {
		
		List<PkpmFileInfo> list = fileInfoMapper.fileList();
		return list;
	}


	@Override
	public PkpmFileInfo select(PkpmFileInfo fileInfo) {
		
		List<PkpmFileInfo> list = fileInfoMapper.select(fileInfo );
		if( list!= null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileName
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileInfoDao#countByName(java.lang.String)  
	 */  
	@Override
	public long countByName(String fileName) {
		// TODO Auto-generated method stub
		return 0;
	}


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param fileName
	 * @param beginPos
	 * @param pageSize
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileInfoDao#filePageListByName(java.lang.String, java.lang.Integer, java.lang.Integer)  
	 */  
	    
	@Override
	public List<PkpmFileInfo> filePageListByName(String fileName, Integer beginPos, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

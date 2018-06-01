package com.pkpmdesktopcloud.mediacontentserver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.mediacontentserver.dao.PkpmFileInfoDao;
import com.pkpmdesktopcloud.mediacontentserver.dao.mapper.PkpmFileInfoMapper;
import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;

@Repository
public class PkpmFileInfoDaoImpl implements PkpmFileInfoDao{

	@Resource
	private PkpmFileInfoMapper fileInfoMapper;
	  
	@Override
	public List<PkpmFileInfo> fileList() {
		
		List<PkpmFileInfo> list = fileInfoMapper.fileList();
		return list;
	}


	@Override
	public PkpmFileInfo selectOne(PkpmFileInfo fileInfo) {
		
		List<PkpmFileInfo> list = fileInfoMapper.select(fileInfo );
		if( list!= null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}

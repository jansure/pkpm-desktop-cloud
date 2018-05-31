package com.pkpmcloud.fileserver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.pkpmcloud.fileserver.dao.PkpmFileConfigDao;
import com.pkpmcloud.fileserver.dao.mapper.PkpmFileConfigMapper;
import com.pkpmcloud.fileserver.domain.PkpmFileConfig;

@Repository
public class PkpmFileConfigDaoImpl implements PkpmFileConfigDao{

	@Resource
	private PkpmFileConfigMapper fileConfigMapper;
	
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see com.pkpmcloud.fileserver.dao.PkpmFileConfigDao#selectAll()  
	 */  
	    
	@Override
	public List<PkpmFileConfig> selectAll() {
		PkpmFileConfig fileConfig = new PkpmFileConfig();
		return fileConfigMapper.select(fileConfig );
	}
	  
	

	
}

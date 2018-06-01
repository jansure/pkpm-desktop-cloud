package com.pkpmcloud.fileserver.dao;

import java.util.List;

import com.pkpmcloud.fileserver.domain.PkpmFileConfig;

public interface PkpmFileConfigDao {
	
	 List<PkpmFileConfig> selectAll();

}

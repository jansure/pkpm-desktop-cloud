package com.pkpmdesktopcloud.mediacontentserver.dao;

import java.util.List;

import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileConfig;

public interface PkpmFileConfigDao {
	
	 List<PkpmFileConfig> selectAll();

}

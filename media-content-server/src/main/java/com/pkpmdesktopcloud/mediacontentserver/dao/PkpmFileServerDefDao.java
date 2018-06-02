package com.pkpmdesktopcloud.mediacontentserver.dao;

import java.util.List;

import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileServerDef;

public interface PkpmFileServerDefDao {
	
	 List<PkpmFileServerDef> select(PkpmFileServerDef fileServerDef );

}

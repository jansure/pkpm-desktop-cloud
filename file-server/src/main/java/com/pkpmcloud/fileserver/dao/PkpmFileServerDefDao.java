package com.pkpmcloud.fileserver.dao;

import java.util.List;

import com.pkpmcloud.fileserver.domain.PkpmFileServerDef;

public interface PkpmFileServerDefDao {
	
	 List<PkpmFileServerDef> select(PkpmFileServerDef fileServerDef );

}

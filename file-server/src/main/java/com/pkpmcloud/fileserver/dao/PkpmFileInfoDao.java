package com.pkpmcloud.fileserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Update;

import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

public interface PkpmFileInfoDao {
	
	PkpmFileInfo selectByMd5(String md5);
    
    Integer insert(PkpmFileInfo fileInfo);

    Integer update(PkpmFileInfo fileInfo);

}

package com.pkpmdesktopcloud.mediacontentserver.dao;

import java.util.List;

import com.pkpmdesktopcloud.mediacontentserver.domain.PkpmFileInfo;

public interface PkpmFileInfoDao {

	List<PkpmFileInfo> fileList();

	PkpmFileInfo selectOne(PkpmFileInfo fileInfo);

}

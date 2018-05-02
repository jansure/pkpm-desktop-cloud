package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;

import java.util.List;


public interface PkpmSysConfigDAO {
    List<PkpmSysConfig> getPkpmSysConfig(PkpmSysConfig sysConfig);
    PkpmSysConfig getPkpmSysConfigByKey(String key);
}

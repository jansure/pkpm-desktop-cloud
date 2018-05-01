package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;

import java.util.List;

public interface SysConfigDAO {
    List<SysConfig> getSysConfig(SysConfig sysConfig);
    SysConfig getSysConfigByKey(String key);
}

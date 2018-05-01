package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import com.desktop.utils.StringUtil;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SysConfigDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigDAO sysConfigDAO;

    @Override
    public SysConfig getSysConfigByKey(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key),"key不能为空!");
        SysConfig sysConfig = sysConfigDAO.getSysConfigByKey(key);
        Preconditions.checkArgument(sysConfig!=null,String.format("%s对应的配置为空",key));
        return sysConfig;
    }

}

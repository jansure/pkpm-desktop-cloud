package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmSysConfigDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PkpmSysConfigServiceImpl implements PkpmSysConfigService {
	
	private static final String SYS_CONFIG_ID = "sysconfig";
	
    @Resource
    private PkpmSysConfigDAO sysConfigDAO;

    @Override
    public PkpmSysConfig getPkpmSysConfigByKey(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key),"key不能为空!");
        PkpmSysConfig sysConfig = sysConfigDAO.getPkpmSysConfigByKey(key);
        Preconditions.checkArgument(sysConfig!=null,String.format("%s对应的配置为空",key));
        return sysConfig;
    }

}

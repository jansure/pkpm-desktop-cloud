package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SysConfigDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.SysConfigMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import org.apache.commons.collections4.CollectionUtils;
import javax.annotation.Resource;
import java.util.List;

public class SysConfigImpl implements SysConfigDAO{
    @Resource
    private SysConfigMapper mapper;

    @Override
    public List<SysConfig> getSysConfig(SysConfig sysConfig) {
        List<SysConfig> list = mapper.select(sysConfig);
        if(CollectionUtils.isNotEmpty(list))
            return list;
        return null;
    }
    @Override
    public SysConfig getSysConfigByKey(String key) {
        SysConfig criteria = new SysConfig();
        criteria.setKey(key);
        List<SysConfig> list = mapper.select(criteria);
        if(CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
}

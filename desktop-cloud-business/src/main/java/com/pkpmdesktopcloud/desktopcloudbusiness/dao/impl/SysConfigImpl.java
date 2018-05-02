package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmSysConfigDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmSysConfigMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import org.apache.commons.collections4.CollectionUtils;
import javax.annotation.Resource;
import java.util.List;

public class PkpmSysConfigImpl implements PkpmSysConfigDAO{
    @Resource
    private PkpmSysConfigMapper mapper;

    @Override
    public List<PkpmSysConfig> getPkpmSysConfig(PkpmSysConfig sysConfig) {
        List<PkpmSysConfig> list = mapper.select(sysConfig);
        if(CollectionUtils.isNotEmpty(list))
            return list;
        return null;
    }
    @Override
    public PkpmSysConfig getPkpmSysConfigByKey(String key) {
        PkpmSysConfig criteria = new PkpmSysConfig();
        criteria.setKey(key);
        List<PkpmSysConfig> list = mapper.select(criteria);
        if(CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
}

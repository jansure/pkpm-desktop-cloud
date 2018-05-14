package com.pkpmcloud.dao.impl;

import com.pkpmcloud.dao.UrlDefDao;
import com.pkpmcloud.dao.mapper.UrlDefMapper;
import com.pkpmcloud.model.UrlDef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@Repository
public class UrlDefDaoImpl implements UrlDefDao{

    @Resource
    UrlDefMapper mapper;

    @Override
    public UrlDef getUrlDef(String serviceName) {
        UrlDef urlDef = new UrlDef();
        urlDef.setServiceName(serviceName);
        UrlDef res = mapper.listUrl(urlDef).get(0);
        return  res;
    }
}

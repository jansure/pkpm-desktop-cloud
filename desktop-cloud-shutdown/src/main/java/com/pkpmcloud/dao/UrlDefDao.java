package com.pkpmcloud.dao;

import com.pkpmcloud.model.UrlDef;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
public interface UrlDefDao {
    UrlDef getUrlDef(String serviceName);
}

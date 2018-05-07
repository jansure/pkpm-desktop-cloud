package com.idserver.service.impl;

import com.idserver.dao.IdentifyDAO;
import com.idserver.model.Identify;
import com.idserver.service.IdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@Service
public class IdentifyServiceImpl implements IdentifyService {

    @Autowired
    IdentifyDAO dao;

    /**
     * 获取唯一计算机名
     * 同一adId与productName对应的计算机名数字会递增 ,如 lvjian0--lvjian99
     * @author xuhe
     * @param productName, adId
     * @return java.lang.String
     */
    @Override
    @Transactional
    public String getAvailableIdentity(String productName, Integer adId) {


        Identify identify =new Identify();
        identify.setAdId(adId);
        identify.setIdentifyPrefix(productName);
        /**通过adId与productName查询总数，总数即为下一个编码(计数从0开始)*/
        int count = dao.count(identify);
        identify.setIdentifyIndex(count);
        dao.insert(identify);
        return productName+count;
    }
}

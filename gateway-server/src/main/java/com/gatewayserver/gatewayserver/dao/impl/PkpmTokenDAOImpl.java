package com.gatewayserver.gatewayserver.dao.impl;

import com.gateway.common.domain.PkpmToken;
import com.gatewayserver.gatewayserver.dao.PkpmTokenDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmTokenMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: yangpengfei
 * @Date: 2018/3/23
 */
@Repository
public class PkpmTokenDAOImpl implements PkpmTokenDAO {

    @Resource
    private PkpmTokenMapper mapper;

    @Override
    public Integer update(PkpmToken pkpmToken) {
        return mapper.update(pkpmToken);
    }

    @Override
    public Integer insert(PkpmToken pkpmToken) {
        return mapper.insert(pkpmToken);
    }

    @Override
    public PkpmToken select(PkpmToken pkpmToken) {
        List<PkpmToken> list = mapper.select(pkpmToken);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
}

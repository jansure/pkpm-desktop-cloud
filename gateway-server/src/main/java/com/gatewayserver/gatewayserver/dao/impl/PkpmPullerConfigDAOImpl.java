package com.gatewayserver.gatewayserver.dao.impl;

import com.gateway.common.domain.PkpmPullerConfig;
import com.gatewayserver.gatewayserver.dao.PkpmPullerConfigDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmPullerConfigMapper;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiulong
 * @ClassName: PkpmPullerConfigDAOImpl
 * @Description: Pull配置Dao实现类
 * @date 2018年4月8日
 */
@Repository
public class PkpmPullerConfigDAOImpl implements PkpmPullerConfigDAO {

    @Resource
    private PkpmPullerConfigMapper mapper;

    @Override
    public List<PkpmPullerConfig> selectAll() {
        PkpmPullerConfig pullerConfig = new PkpmPullerConfig();
        List<PkpmPullerConfig> list = mapper.select(pullerConfig);
        return list;
    }

    @Override
    public Integer update(PkpmPullerConfig pullerConfig) {
        return mapper.update(pullerConfig);
    }

    @Override
    public Integer insert(PkpmPullerConfig pullerConfig) {
        return mapper.insert(pullerConfig);
    }
}

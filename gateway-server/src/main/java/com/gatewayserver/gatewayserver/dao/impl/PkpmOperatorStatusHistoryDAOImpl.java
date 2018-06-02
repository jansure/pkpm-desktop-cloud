package com.gatewayserver.gatewayserver.dao.impl;

import com.gateway.common.domain.PkpmOperatorStatusHistory;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusHistoryDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmOperatorStatusHistoryMapper;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmOperatorStatusHistoryDAOImpl implements PkpmOperatorStatusHistoryDAO {
    @Resource
    private PkpmOperatorStatusHistoryMapper mapper;

    @Override
    public List<PkpmOperatorStatusHistory> select(PkpmOperatorStatusHistory operatorStatusHistory) {
        return mapper.select(operatorStatusHistory);
    }

    @Override
    public Integer update(PkpmOperatorStatusHistory pkpmOperatorStatus) {
        return mapper.update(pkpmOperatorStatus);
    }

    @Override
    public Integer save(PkpmOperatorStatusHistory pkpmOperatorStatus) {

        return mapper.save(pkpmOperatorStatus);

    }

}

package com.gatewayserver.gatewayserver.dao.impl;

import com.gateway.common.domain.PkpmAdDef;
import com.gatewayserver.gatewayserver.dao.PkpmAdDefDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmAdDefMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmAdDefDAOImpl implements PkpmAdDefDAO {

    @Resource
    private PkpmAdDefMapper mapper;

    @Override
    public PkpmAdDef selectById(Integer adId) {
        PkpmAdDef adDef = new PkpmAdDef();
        adDef.setAdId(adId);
        List<PkpmAdDef> list = mapper.select(adDef);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

    @Override
    public PkpmAdDef selectByAdIpAddress(String adIpAddress) {
        PkpmAdDef adDef = new PkpmAdDef();
        adDef.setAdIpAddress(adIpAddress);
        List<PkpmAdDef> list = mapper.select(adDef);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

	@Override
	public List<PkpmAdDef> select(PkpmAdDef pkpmAdDef) {
		return mapper.select(pkpmAdDef);
	}
}

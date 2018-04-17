package com.gatewayserver.gatewayserver.dao.impl;

import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmProjectDefMapper;
import com.gatewayserver.gatewayserver.domain.PkpmProjectDef;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmProjectDefDAOImpl implements PkpmProjectDefDAO {
    @Resource
    private PkpmProjectDefMapper mapper;

    @Override
    public PkpmProjectDef selectById(String projectId) {
    	
        PkpmProjectDef criteria = new PkpmProjectDef();
        criteria.setProjectId(projectId);
        List<PkpmProjectDef> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

	@Override
	public List<PkpmProjectDef> select(PkpmProjectDef pkpmProjectDef) {
		return mapper.select(pkpmProjectDef);
	}
}

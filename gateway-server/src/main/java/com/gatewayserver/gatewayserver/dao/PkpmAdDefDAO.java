package com.gatewayserver.gatewayserver.dao;


import java.util.List;

import com.gateway.common.domain.PkpmAdDef;

public interface PkpmAdDefDAO {
    PkpmAdDef selectById(Integer adId);
    PkpmAdDef selectByAdIpAddress(String adIpAddress);

    /**
     * 查询AD列表
     * @param pkpmAdDef
     * @return List<PkpmAdDef>
     * @author yangpengfei
     * @time 2018年4月12日 下午3:22:30
     */
    List<PkpmAdDef> select(PkpmAdDef pkpmAdDef);
}

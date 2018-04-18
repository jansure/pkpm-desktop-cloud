package com.gatewayserver.gatewayserver.service;

import com.gateway.common.domain.CommonRequestBean;

public interface StrategyService {
    /**
     * 查询产品策略
     *
     * @param requestBean
     * @return
     */
	CommonRequestBean queryStrategy(CommonRequestBean requestBean);

    /**
     * 查询产品套餐列表
     *
     * @param commonRequestBean,
     * @return
     */

    Integer updateStrategy(CommonRequestBean commonRequestBean);

}

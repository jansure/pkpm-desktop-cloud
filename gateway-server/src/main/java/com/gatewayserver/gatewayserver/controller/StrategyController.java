package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.service.StrategyService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("strategy")
public class StrategyController {

    @Resource
    CommonRequestBeanBuilder commonRequestBeanBuilder;
    @Autowired
    private StrategyService strategyService;

    /**
     * 查询产品策略
     *
     * @param commonRequestBean
     * @return
     */
    @PostMapping(value = "queryStrategy")
    public ResultObject queryStrategy(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestProjectId(commonRequestBean);
        CommonRequestBean responseBean = strategyService.queryStrategy(commonRequestBean);
        return ResultObject.success(responseBean, "查询产品策略成功!");

    }

    /**
     * 修改策略
     *
     * @param commonRequestBean
     * @return
     */
	@PostMapping(value = "/updateStrategy")
    public ResultObject updateStrategy(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkUpdateStrategy(commonRequestBean);
        Integer statusCode = strategyService.updateStrategy(commonRequestBean);
        return	ResultObject.success(statusCode, "修改策略成功!");

    }
}

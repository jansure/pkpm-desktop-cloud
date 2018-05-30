package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.PkpmCloudStrategyVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudStrategyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(description ="策略操作")
@RequestMapping("strategy")
public class StrategyController {

    @Autowired
    private PkpmCloudStrategyService strategyService;

    /**
     * 查询产品策略
     *
     * @param commonRequestBean
     * @return
     */
    @ApiOperation("查询用户策略")
    @PostMapping(value = "getStrategyByQuery")
    public ResultObject getStrategyByQuery(String userName, int pageSize, int pageNo) {

    	List<PkpmCloudStrategyVO> strategyVOList = strategyService.getStrategyByQuery(userName, pageSize, pageNo);
        return ResultObject.success(strategyVOList, "查询产品策略成功!");

    }

    /**
     * 修改策略
     *
     * @param commonRequestBean
     * @return
     */
	@PostMapping(value = "/updateStrategy")
    public ResultObject updateStrategy() {

//        strategyService.updateStrategy();
        return	ResultObject.success("修改策略成功!");

    }
}

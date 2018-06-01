package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.strategy.Policies;
import com.google.common.base.Preconditions;
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
     * 查询用户下的策略
     *
     * @param commonRequestBean
     * @return
     */
    @ApiOperation("查询用户策略")
    @PostMapping(value = "getStrategyByQuery")
    public ResultObject getStrategyByQuery(String userName, int pageSize, int pageNo) {

    	Preconditions.checkArgument(pageSize < 1, "pageSize不能小于0");
    	List<PkpmCloudStrategyVO> strategyVOList = strategyService.getStrategyByQuery(userName, pageSize, pageNo);
        return ResultObject.success(strategyVOList, "查询产品策略成功!");

    }
    
    /**
     * 查询特定桌面策略
     *
     * @param commonRequestBean
     * @return
     */
    @ApiOperation("查询特定桌面策略")
    @PostMapping(value = "getStrategyByDesktopId")
    public ResultObject getStrategyByDesktopId(String desktopId, String projectId) {

    	Preconditions.checkArgument(StringUtils.isEmpty(projectId), "projectId不能为空");
    	Preconditions.checkArgument(StringUtils.isEmpty(desktopId), "desktopId不能为空");
    	Policies policies = strategyService.getStrategyByDesktopId(desktopId);
        return ResultObject.success(policies, "查询产品策略成功!");

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

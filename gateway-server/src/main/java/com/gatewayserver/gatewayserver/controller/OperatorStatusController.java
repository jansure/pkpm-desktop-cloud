package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.service.IOperatorStatusService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/operator")
public class OperatorStatusController {

    @Resource
    private IOperatorStatusService operatorStatusService;

    @PostMapping(value = "/queryComputerName")
    public ResultObject queryComputerName(@RequestBody CommonRequestBean commonRequestBean){

        CommonRequestBeanUtil.checkqueryComputerName(commonRequestBean);
        String desktopId = commonRequestBean.getDesktopId();
        String computerName = operatorStatusService.queryComputerName(desktopId);
        if(StringUtils.isEmpty(computerName)){
            return ResultObject.failure("查询计算机名失败");
        }
        return  ResultObject.success(computerName);
    }
}

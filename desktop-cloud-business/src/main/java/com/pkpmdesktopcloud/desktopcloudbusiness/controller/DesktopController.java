package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.Desktop;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.DesktopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(value = "/desktop",description = "操作桌面")
@RequestMapping(value = "/desktop")
public class DesktopController {

    @Autowired
	private DesktopService desktopService;

    @ApiOperation("操作桌面接口")
    @PostMapping(value = "/operateDesktop")
    public ResultObject operateDesktop(@RequestBody CommonRequestBean commonRequestBean) {

        checkParams(commonRequestBean);
        String msg = desktopService.operateDesktop(commonRequestBean);
        if(StringUtils.isEmpty(msg)){
            return ResultObject.failure("操作桌面状态失败");
        }
        return ResultObject.success(msg);

    }


    private void checkParams(CommonRequestBean commonRequestBean){

        String desktopOperatorType =null;
        String desktopId = null;

        Integer userId = commonRequestBean.getUserId();
        String userName = commonRequestBean.getUserName();
        Long subsId = commonRequestBean.getSubsId();
        String projectId = commonRequestBean.getProjectId();
        Integer adId = commonRequestBean.getAdId();

        List<Desktop> desktops = commonRequestBean.getDesktops();
        if(null !=desktops){
            Desktop desktop = desktops.get(0);
            desktopOperatorType = desktop.getDesktopOperatorType();
            desktopId = desktop.getDesktopId();
        }

        Preconditions.checkArgument(StringUtils.isNotBlank(desktopOperatorType), "desktopOperatorType不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(desktopId), "desktopId不能为空");
        Preconditions.checkArgument(null != userId, "userId不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(userName), "userName不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(projectId), "projectId不能为空");
        Preconditions.checkArgument(null != adId, "adId不能为空");


    }



}

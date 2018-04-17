package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.record.Records;
import com.gatewayserver.gatewayserver.dto.resp.userlist.UserList;
import com.gatewayserver.gatewayserver.service.UserService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService desktopUserService;
    @Resource
    private CommonRequestBeanBuilder commonRequestBeanBuilder;

    /**
     * 查询桌面用户列表
     *
     * @param commonRequestBean
     * @return
     */
    @PostMapping(value = "queryDesktopUserList")
    public ResultObject queryDesktopUserList(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestProjectId(commonRequestBean);
        UserList userList = desktopUserService.queryDesktopUserList(commonRequestBean);
        return ResultObject.success(userList, "查询桌面用户列表成功");

    }

    /**
     * 查询用户登录记录
     *
     * @param commonRequestBean
     * @return
     */
    @PostMapping(value = "queryUserLoginRecord")
    public ResultObject queryUserLoginRecord(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestProjectId(commonRequestBean);
        Records records = desktopUserService.queryUserLoginRecord(commonRequestBean);
        return ResultObject.success(records, "查询用户登录记录成功");


    }
}

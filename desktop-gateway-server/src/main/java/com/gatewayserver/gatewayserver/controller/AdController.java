package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.AdUser;
import com.gatewayserver.gatewayserver.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
@RestController
@RequestMapping("/ad")
public class AdController {

    @Autowired
    AdService adService;

    /**
     * @Description: 添加用户至AD域 根据ADUser内的AdId字段插入对应的AD域；
     * @Param: * @param adUser
     * @Return ResultObject
     */
    @RequestMapping("/user/add")
    public ResultObject addUser(CommonRequestBean requestBean) {
        adService.addAdUser(requestBean);
        return ResultObject.success("用户添加成功");
    }
    /**
     * @Description: 查询某一AD域下的所有用户；
     * @Param: * @param adId
     * @Return ResultObject
     */

    @RequestMapping("/user/count")
    public ResultObject get(@RequestParam("adIpAddress") String adIpAddress) {
        int count = adService.getUserCountByAdIpAddress(adIpAddress);
        return ResultObject.success(count,"SUCCESS");
    }

    /**
     * @Description: 查询某一AD域下的所有用户；
     * @Param: * @param adId
     * @Return ResultObject
     */

    @RequestMapping("/queryUserList")
    public ResultObject selectUsers(@RequestParam("adId") Integer adId) {
        List<AdUser> adUsers= adService.getUsersByAdId(adId);
        return ResultObject.success(adUsers,"SUCCESS");
    }

    /**
     * @Description: 查询某一AD域下的所有计算机；
     * @Param: * @param adId
     * @Return ResultObject
     */
    @RequestMapping("/queryComputerList")
    public ResultObject selectComputer(@RequestParam("adId") Integer adId) {
        ResultObject resultObject = adService.getComputersByAdId(adId);
        return resultObject;
    }

    /**
     * @Description: 检查用户是否存在
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping("/checkUser")
    public boolean checkUser(@RequestParam("userName") String userName, @RequestParam("adId") Integer adId) {
        boolean result = adService.checkUser(userName, adId);
        return result;
    }

    /**
     * @Description: 根据用户名删除用户
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping("/deleteUser")
    public ResultObject deleteUser(@RequestParam("userName") String userName, @RequestParam("adId") Integer adId) {
        ResultObject resultObject = adService.deleteUser(userName, adId);
        return resultObject;
    }

    /**
     * @Description: 根据计算机名删除计算机
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping("/deleteComputer")
    public ResultObject deleteComputer(@RequestParam("computerName") String computerName, @RequestParam("adId") Integer adId) {
        ResultObject resultObject = adService.deleteUser(computerName, adId);
        return resultObject;
    }


}

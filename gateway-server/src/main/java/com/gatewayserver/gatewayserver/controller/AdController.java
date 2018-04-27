package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.ad.AdComputer;
import com.gateway.common.dto.ad.AdUser;
import com.gatewayserver.gatewayserver.service.AdService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
@RestController
@RequestMapping("ad")
public class AdController {

    @Autowired
    AdService adService;

    /**
     * @Description: 添加用户至AD域 根据ADUser内的AdId字段插入对应的AD域；
     * @Param: * @param adUser
     * @Return ResultObject
     */
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public ResultObject addUser(@RequestBody CommonRequestBean requestBean) {
        String response = adService.addAdUser(requestBean);
        return ResultObject.success(response,"用户添加成功");
    }
    /**
     * @Description: 添加用户至AD域 根据ADUser内的AdId字段插入对应的AD域；
     * @Param: * @param adUser
     * @Return ResultObject
     */
    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public ResultObject updateUser(@RequestBody CommonRequestBean requestBean) {
        System.out.println("AD服务Controller进入成功"+requestBean);
        String response = adService.updateAdUser(requestBean);
        return ResultObject.success(response,"密码修改成功");
    }
    /**
     * @Description: 添加用户至AD域 根据ADUser内的AdId字段插入对应的AD域；
     * @Param: * @param adUser
     * @Return ResultObject
     */
    @GetMapping(value = "/computer/getAvailableName")
    public ResultObject updateUser(@RequestParam String computerName,@RequestParam Integer adId) {
        String response = adService.getAvailableComputerName(computerName,adId);
        return ResultObject.success(response,"获取成功");
    }
    /**
     * @Description: 查询某一AD域下的所有用户；
     * @Param: * @param adId
     * @Return ResultObject
     */

    @RequestMapping(value = "user/list",method = RequestMethod.GET)
    public ResultObject selectUsers(@RequestParam("adId") Integer adId) {
        List<AdUser> adUsers= adService.getUsersByAdId(adId);
        return ResultObject.success(adUsers,"SUCCESS");
    }


    /**
     * @Description: 根据用户名删除用户
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping(value = "user/delete",method = RequestMethod.POST)
    public ResultObject deleteUser(CommonRequestBean requestBean) {
        Preconditions.checkNotNull(requestBean);
        Integer adId = requestBean.getAdId();
        String userName = requestBean.getUserName();
        adService.deleteUser(userName, adId);
        return ResultObject.success(userName,"删除成功");
    }

    /**
     * @Description: 检查用户名是否存在
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping(value = "user/check",method = RequestMethod.POST)
    public ResultObject checkUser(@RequestParam("userName") String userName, @RequestParam("adId") Integer adId) {
        Boolean result = adService.checkUser(userName, adId);
        if (result)
            return ResultObject.success(null, "用户存在");
        else
            return ResultObject.success(null, "用户不存在！");
    }

    /**
     * @Description: 检查计算机名是否存在
     * @Param: * @param userName,adId
     * @Return ResultObject
     */

    @RequestMapping(value = "computer/check",method = RequestMethod.POST)
    public ResultObject checkComputer(@RequestBody CommonRequestBean requestBean) {
        String computerName =requestBean.getComputerName();
        Integer adId =requestBean.getAdId();
        Boolean result = adService.checkComputer(computerName, adId);
        if (result)
            return ResultObject.success(null, "计算机名存在");
        else
            return ResultObject.success(null, "计算机名不存在！");
    }

    /**
     * @Description: 查询AdHost下的用户数量
     * @Param: * @param adId
     * @Return ResultObject
     */
    @RequestMapping(value = "/user/count",method = RequestMethod.GET)
    public ResultObject get(@RequestParam("adIpAddress") String adIpAddress) {
        int count = adService.getUserCountByAdIpAddress(adIpAddress);
        return ResultObject.success(count,"SUCCESS");
    }

    /**
     * @Description: 查询AdHost 某一组织 下的用户数量
     * @Param: * @param adId
     * @Return ResultObject
     */
    @RequestMapping(value = "/user/count/ou",method = RequestMethod.GET)
    public ResultObject get(@RequestParam("adId") Integer adId) {
        int count = adService.getUserOuCountByAdId(adId);
        return ResultObject.success(count,"列表获取成功");
    }

    /**
     * @Description: 查询某一AD域下的所有计算机；
     * @Param: * @param adId
     * @Return ResultObject
     */
    @RequestMapping("computer/list")
    public ResultObject selectComputer(@RequestParam("adId") Integer adId) {
        List<AdComputer> computers= adService.getComputersByAdId(adId);
        return ResultObject.success(computers,"列表获取成功");
    }

    /**
     * @Description: 根据计算机名删除计算机
     * @Param: * @param userName,adId
     * @Return ResultObject
     */
    @RequestMapping(value = "computer/delete",method = RequestMethod.POST)
    public ResultObject deleteComputer(CommonRequestBean requestBean) {
        Preconditions.checkNotNull(requestBean);
        Integer adId = requestBean.getAdId();
        String computerName = requestBean.getDesktops().get(0).getComputerName();
        adService.deleteComputer(computerName, adId);
        return ResultObject.success(computerName,"删除成功");
    }


}

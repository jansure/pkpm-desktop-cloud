package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.service.StatusService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kongbaoping
 * 查询桌面创建、删除、重启、关闭、启动的状态
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    /**
     * @param commonRequestBean 而且传入adId userId projectId subsId userName
     * @return ResultObject
     */
    @PostMapping("desktopStatus")
    public ResultObject desktopStatus(@RequestBody CommonRequestBean commonRequestBean) {

    	  CommonRequestBeanUtil.checkStatusCommonRequestBean(commonRequestBean);
          String statusObject = null;
          try {
  			 statusObject = statusService.queryDesktopStatus(commonRequestBean);
  		} catch (Exception e) {
  			e.printStackTrace();
  			return ResultObject.failure(e.getMessage());
  		}
          
          return ResultObject.success(statusObject);
    }

    /**
     * @param commonRequestBean 而且传入adId userId projectId subsId userName
     * @return ResultObject
     */
    @PostMapping("operateStatus")
    public ResultObject operateStatus(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkStatusCommonRequestBean(commonRequestBean);
        ResultObject resultObject = statusService.queryOperateStatus(commonRequestBean);
        return resultObject;

    }


}

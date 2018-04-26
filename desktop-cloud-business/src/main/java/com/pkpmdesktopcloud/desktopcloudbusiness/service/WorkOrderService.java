package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;

public interface WorkOrderService {

	PageBean<WorkOrder> findWorkOrderList(Integer currentPage, Integer pageSize);

	Integer updateWorkOrder(Integer id,Long workId);
	
	boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber);
	
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);
	
	/**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
    List<Map<String, String>> getClientInfo(String userMobileNumber, Long workId);

}

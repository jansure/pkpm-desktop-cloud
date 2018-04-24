package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;

public interface WorkOrderService {

	PageBean<WorkOrder> findWorkOrderList(Integer currentPage, Integer pageSize);

	Integer updateWorkOrder(Integer id,Long workId);
	
	boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber);
	
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);

}

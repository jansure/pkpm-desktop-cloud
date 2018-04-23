package com.gateway.cloud.business.service;

import java.util.List;

import com.gateway.cloud.business.entity.WorkOrder;
import com.gateway.cloud.business.utils.ResponseResult;

public interface WorkOrderService {

	ResponseResult findWorkOrderList(Integer currentPage, Integer pageSize);

	ResponseResult updateWorkOrder(Integer id,Long workId);
	
	boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber);
	
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);

}

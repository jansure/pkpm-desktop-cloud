package com.cabr.pkpm.service;

import java.util.List;

import com.cabr.pkpm.entity.workorder.WorkOrder;
import com.cabr.pkpm.utils.ResponseResult;

public interface IWorkOrderService {

	ResponseResult findWorkOrderList(Integer currentPage, Integer pageSize);

	ResponseResult updateWorkOrder(Integer id,Long workId);
	
	boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber);
	
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);

}

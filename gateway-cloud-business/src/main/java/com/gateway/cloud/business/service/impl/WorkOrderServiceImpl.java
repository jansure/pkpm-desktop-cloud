package com.gateway.cloud.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gateway.cloud.business.entity.WorkOrder;
import com.gateway.cloud.business.mapper.SubsDetailsMapper;
import com.gateway.cloud.business.mapper.WorkOrderMapper;
import com.gateway.cloud.business.service.WorkOrderService;
import com.gateway.cloud.business.utils.ResponseResult;
import com.gateway.cloud.business.utils.sdk.PageBean;
import com.github.pagehelper.PageHelper;
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
	
	@Autowired
	private WorkOrderMapper workOrderMapper;
	@Autowired
	private SubsDetailsMapper subsDetailsMapper;
	
	protected ResponseResult result=new ResponseResult();  
	
	public ResponseResult findWorkOrderList(Integer currentPage, Integer pageSize) {
		
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<WorkOrder> workOrderList = workOrderMapper.findWorkOrderList();
			PageBean<WorkOrder> pageData = new PageBean<>(currentPage,pageSize,workOrderList.size());
			pageData.setItems(workOrderList);
			this.result.set("查询成功", 1, pageData.getTotalNum()+"", workOrderList);
		} catch (Exception e) {
			this.result.set("查询失败", 0);
			e.printStackTrace();
		}
	    return this.result;
	}
	
	public ResponseResult updateWorkOrder(Integer id, Long workId) {
		
		Integer workOrderCount = workOrderMapper.updateWorkOrder(id);
		Integer  subsDetailsCount= subsDetailsMapper.updateSubsDetailsStatus(workId);
		
		if(workOrderCount< 1 || subsDetailsCount< 1){
			this.result.set("开通失败,请重新开通", 0);
		}
		this.result.set("开通成功", 1);
		return this.result;
	}

	@Override
	public boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber) {
		
		if(workOrderMapper.updatePasswordOrMobileNumberByUserID(userId, userLoginPassword, userMobileNumber)>0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<WorkOrder> findWorkOrderListByUserId(Integer userId) {
		return workOrderMapper.findWorkOrderListByUserId(userId);
	}
}

package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desktop.utils.exception.Exceptions;
import com.github.pagehelper.PageHelper;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.WorkOrderDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.WorkOrderService;
import com.pkpmdesktopcloud.desktopcloudbusiness.utils.ResponseResult;
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
	
	@Autowired
	private WorkOrderDAO workOrderMapper;
	@Autowired
	private SubsDetailsDAO subsDetailsMapper;
	
	public PageBean<WorkOrder> findWorkOrderList(Integer currentPage, Integer pageSize) {
		
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<WorkOrder> workOrderList = workOrderMapper.findWorkOrderList();
			PageBean<WorkOrder> pageData = new PageBean<>(currentPage,pageSize,workOrderList.size());
			pageData.setItems(workOrderList);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw Exceptions.newBusinessException("没有要获取状态的任务");
	   
	}
	
	public Integer updateWorkOrder(Integer id, Long workId) {
		
		Integer workOrderCount = workOrderMapper.updateWorkOrder(id);
		Integer  subsDetailsCount= subsDetailsMapper.updateSubsDetailsStatus(workId);
		
		return workOrderCount;
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

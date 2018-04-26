package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desktop.utils.exception.Exceptions;
import com.github.pagehelper.PageHelper;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.WorkOrderDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.WorkOrderService;
@Service
public class WorkOrderServiceImpl implements WorkOrderService {
	
	@Autowired
	private WorkOrderDAO workOrderDAO;
	@Autowired
	private SubsDetailsDAO subsDetailsDao;
	
	public PageBean<WorkOrder> findWorkOrderList(Integer currentPage, Integer pageSize) {
		
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<WorkOrder> workOrderList = workOrderDAO.findWorkOrderList();
			PageBean<WorkOrder> pageData = new PageBean<>(currentPage,pageSize,workOrderList.size());
			pageData.setItems(workOrderList);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw Exceptions.newBusinessException("没有要获取状态的任务");
	   
	}
	
	public Integer updateWorkOrder(Integer id, Long workId) {
		
		Integer workOrderCount = workOrderDAO.updateWorkOrder(id);
		Integer  subsDetailsCount= subsDetailsDao.updateSubsDetailsStatus(workId);
		
		return workOrderCount + subsDetailsCount;
	}

	@Override
	public boolean updatePasswordOrMobileNumber(Integer userId, String userLoginPassword, String userMobileNumber) {
		
		if(workOrderDAO.updatePasswordOrMobileNumberByUserID(userId, userLoginPassword, userMobileNumber)>0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<WorkOrder> findWorkOrderListByUserId(Integer userId) {
		return workOrderDAO.findWorkOrderListByUserId(userId);
	}
	
	@Override
	public List<Map<String, String>> getClientInfo(String userMobileNumber, Long workId) {
		List<Map<String, String>> clientInfoList = workOrderDAO.getClientInfo(userMobileNumber, workId);
		return clientInfoList;
	}
}

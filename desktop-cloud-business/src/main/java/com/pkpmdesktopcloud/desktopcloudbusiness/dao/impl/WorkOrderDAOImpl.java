package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.WorkOrderDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.WorkOrderMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;

@Repository
public class WorkOrderDAOImpl implements WorkOrderDAO{
	
	@Autowired
	private WorkOrderMapper workOrderMapper;

    /**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
	@Override
	public List<Map<String, String>> getClientInfo(String userMobileNumber, Long workId){
		WorkOrder workOrder = new WorkOrder();
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		if(list != null && list.size() > 0) {
			//obj to map
		}
		
		return null;
	}

	@Override
	public Integer saveWorkerOrder(List<WorkOrder> workOrderList) {
		int num = 0;
		
		if(workOrderList != null && workOrderList.size() > 0) {
			
			for(WorkOrder workOrder : workOrderList) {
				
				workOrder.setId(null);
				num += workOrderMapper.insert(workOrder);
			}
		}
		
		return num;
	}

	@Override
	public List<WorkOrder> findWorkOrderList(){
		
		WorkOrder workOrder = new WorkOrder();
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		return list;
		
	}

	@Override
	public Integer updateWorkOrder(Integer id) {
		WorkOrder workOrder = new WorkOrder();
		workOrder.setId(id);
		workOrder.setStatus(1);
		workOrder.setOpenTime(LocalDateTime.now());
		
		return workOrderMapper.update(workOrder);
	}
	
	
	/**
	 * 更新worker_order表
	 * @param
	 * @return
	 */
	@Override
	public int updatePasswordOrMobileNumberByUserID(Integer userId,  String userLoginPassword, String userMobileNumber) {
		int num = 0;
		
		//先获取用户相关信息
		WorkOrder workOrder = new WorkOrder();
		workOrder.setUserId(userId);
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(WorkOrder workOrderItem : list) {
				
				workOrder.setId(workOrderItem.getId());
				workOrder.setUserLoginPassWord(userLoginPassword);
				workOrder.setUserMobileNumber(userMobileNumber);
				num += workOrderMapper.update(workOrder);
			}
		}
		
		return num;
	}

	/**
	 * 根据userId,workId,productId查询主机Ip
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return 
	 */
	@Override
	public List<String> findHostIp(Integer userId, Long workId, Integer productId) {
		WorkOrder workOrder = new WorkOrder();
		workOrder.setUserId(userId);
		workOrder.setWorkId(workId);
		workOrder.setProductId(productId);
		
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		if(list != null && list.size() > 0) {
			return list.stream().map(WorkOrder::getHostIp).collect(Collectors.toList());
		}
		
		return null;
	}
	

	/**
	 * 根据userId,workId,productId查询开户状态
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return
	 *  
	 */
	@Override
	public List<Integer> findStatus(Integer userId, Long workId, Integer productId) {
		
		WorkOrder workOrder = new WorkOrder();
		workOrder.setUserId(userId);
		workOrder.setWorkId(workId);
		workOrder.setProductId(productId);
		
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		if(list != null && list.size() > 0) {
			return list.stream().map(WorkOrder::getStatus).collect(Collectors.toList());
		}
		
		return null;
	}
	
	/**
	 * 根据userId,查询工单表
	 * @param userId 
	 * @return
	 *  
	 */
	@Override
	public List<WorkOrder> findWorkOrderListByUserId(Integer userId){

		WorkOrder workOrder = new WorkOrder();
		workOrder.setUserId(userId);
		
		List<WorkOrder> list = workOrderMapper.findWorkOrderList(workOrder);
		return list;
	}
	
}

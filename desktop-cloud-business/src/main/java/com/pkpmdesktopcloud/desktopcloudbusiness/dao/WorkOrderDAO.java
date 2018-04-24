package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;

public interface WorkOrderDAO {

	Integer saveWorkerOrder(List<WorkOrder> workOrderList);

	List<WorkOrder> findWorkOrderList();

	Integer updateWorkOrder(Integer id);
	
	/**
	 * 更新worker_order表
	 * @param
	 * @return
	 */
	int updatePasswordOrMobileNumberByUserID(Integer userId, String userLoginPassword, @Param("userMobileNumber") String userMobileNumber); 

	/**
	 * 根据userId,workId,productId查询主机Ip
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return 
	 */
	List<String> findHostIp(Integer userId, Long workId, Integer productId);
	

	/**
	 * 根据userId,workId,productId查询开户状态
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return
	 *  
	 */
	List<Integer> findStatus(Integer userId, Long workId, Integer productId);
	
	/**
	 * 根据userId,查询工单表
	 * @param userId 
	 * @return
	 *  
	 */
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);
}

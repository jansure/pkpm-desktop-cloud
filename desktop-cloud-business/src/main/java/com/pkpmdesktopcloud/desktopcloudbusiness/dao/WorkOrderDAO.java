package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;

public interface WorkOrderDAO {
	
	/**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
    List<Map<String, String>> getClientInfo(@Param("userMobileNumber") String userMobileNumber, @Param("workId") Long workId);

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

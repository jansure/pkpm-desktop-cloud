package com.gateway.cloud.business.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gateway.cloud.business.entity.WorkOrder;

@Mapper
public interface WorkOrderMapper {

	Integer saveWorkerOrder(List<WorkOrder> workOrderList);

	List<WorkOrder> findWorkOrderList();

	Integer updateWorkOrder(Integer id);
	
	/**
	 * 更新worker_order表
	 * @param
	 * @return
	 */
	int updatePasswordOrMobileNumberByUserID(@Param("userId") Integer userId, @Param("userLoginPassword") String userLoginPassword, @Param("userMobileNumber") String userMobileNumber); 

	/**
	 * 根据userId,workId,productId查询主机Ip
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return 
	 */
	List<String> findHostIp(@Param("userId") Integer userId, @Param("workId") Long workId, @Param("productId") Integer productId);
	

	/**
	 * 根据userId,workId,productId查询开户状态
	 * @param productId 商品id userId 用户id workId订单Id
	 * @return
	 *  
	 */
	List<Integer> findStatus(@Param("userId") Integer userId, @Param("workId") Long workId, @Param("productId") Integer productId);
	
	/**
	 * 根据userId,查询工单表
	 * @param userId 
	 * @return
	 *  
	 */
	List<WorkOrder> findWorkOrderListByUserId(Integer userId);
}

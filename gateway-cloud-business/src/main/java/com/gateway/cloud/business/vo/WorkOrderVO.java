package com.gateway.cloud.business.vo;

import java.util.List;

import com.gateway.cloud.business.entity.WorkOrder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j  
@Data 
public class WorkOrderVO extends WorkOrder {
	@SuppressWarnings("unused")
	private List<ProductNameVO> productNameVOList;
   
	
}

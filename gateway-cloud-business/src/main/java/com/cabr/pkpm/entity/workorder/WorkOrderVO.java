package com.cabr.pkpm.entity.workorder;

import java.util.List;

import com.cabr.pkpm.entity.user.CurrentUserInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j  
@Data 
public class WorkOrderVO extends WorkOrder {
	@SuppressWarnings("unused")
	private List<ProductNameVO> productNameVOList;
   
	
}

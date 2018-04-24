package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j  
@Data 
public class WorkOrderVO extends WorkOrder {
	@SuppressWarnings("unused")
	private List<ProductNameVO> productNameVOList;
   
	
}

package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class SubsDetailsVO extends SubsDetails {
	private Integer regionId;
	private Integer productId;
	private Integer hostConfig;
	private Integer cloudStorageTime;
	
	//private String[] productIds;
	 
	/*public String[] getProductIds() {
		return productIds;
	}
	public void setProductIds(String[] productIds) {
		this.productIds = productIds;
	}*/
}

package com.cabr.pkpm.entity.subsdetails;

import com.cabr.pkpm.entity.component.ComponentInfo;

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

package com.gateway.cloud.business.vo;

import com.gateway.cloud.business.entity.ComponentInfo;
import com.gateway.cloud.business.entity.SubsDetails;

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

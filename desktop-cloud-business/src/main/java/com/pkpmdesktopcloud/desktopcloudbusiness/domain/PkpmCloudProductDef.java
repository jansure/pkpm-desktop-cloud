package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.util.List;

import com.desktop.utils.mybatis.Invisible;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
@Slf4j
@Data
public class PkpmCloudProductDef implements Serializable {

	/** 产品ID */
	private Integer productId;

	/** 产品子类ID */
	private Integer componentId;

	/** 产品名称 */
	private String productName;

	/**
	 * 产品类型 1、结构类产品 2、建筑类产品 3、BIM类产品
	 */
	private Integer productType;

	/** 产品描述 */
	private String productDesc;

	/**
	 * 如果component_type为5的时候此字段生效
	 */
	private String imageId;

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see java.lang.Object#toString()  
	 */  
	@Override
	public String toString() {
		return "PkpmCloudProductDef [productId=" + productId + ", componentId=" + componentId + ", productName="
				+ productName + ", productType=" + productType + ", productDesc=" + productDesc + ", imageId=" + imageId
				+ "]";
	}

	
}

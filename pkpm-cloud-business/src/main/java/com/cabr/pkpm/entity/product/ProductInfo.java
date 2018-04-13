package com.cabr.pkpm.entity.product;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Transient;

import com.cabr.pkpm.entity.component.ComponentInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
@Slf4j  
@Data 
public class ProductInfo implements Serializable {
	private Integer productId;
    private String productName;
    private Integer productType;
    private String productDesc;
    private Integer componentId;
    @Transient
    private List<ProductInfo> children;
    
	@Override
    public String toString() {
        return "ProductInfo [productId=" + productId + ", productType=" + productType + ", productName=" + productName
                + ", children=" + children + "]";
    }

}

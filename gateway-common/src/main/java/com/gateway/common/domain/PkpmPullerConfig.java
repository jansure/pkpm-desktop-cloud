package com.gateway.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxiulong
 * @ClassName: PkpmPullerConfig
 * @Description: Puller配置文件实体类
 * @date 2018年4月8日
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmPullerConfig implements Serializable {
    private Integer id;
    private String operatorType;
    private Integer seconds;
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see java.lang.Object#toString()  
	 */  
	@Override
	public String toString() {
		return "PkpmPullerConfig [id=" + id + ", operatorType=" + operatorType + ", seconds=" + seconds + "]";
	}
    
    
}

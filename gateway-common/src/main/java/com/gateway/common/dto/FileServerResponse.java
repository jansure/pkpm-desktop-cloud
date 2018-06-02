  
/**    
 * @Title: FileServerResponse.java  
 * @Package com.pkpmdesktopcloud.desktopcloudbusiness.dto  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年4月28日  
 * @version V1.0    
 */  
    
package com.gateway.common.dto;

import java.util.Set;

import lombok.Data;

/**  
 * @ClassName: FileServerResponse  
 * @Description: 文件服务返回对象  
 * @author wangxiulong  
 * @date 2018年4月28日  
 *    
 */
@Data
public class FileServerResponse {
	
	private Set<String> files;

}

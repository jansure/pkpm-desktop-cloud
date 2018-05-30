  
/**    
 * @Title: StrategyService.java  
 * @Package com.pkpmdesktopcloud.desktopcloudbusiness.service  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年5月30日  
 * @version V1.0    
 */  
    
package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.dto.PkpmCloudStrategyVO;

/**  
 * @ClassName: StrategyService  
 * @Description: 策略服务类
 * @author wangxiulong  
 * @date 2018年5月30日  
 *    
 */
public interface PkpmCloudStrategyService {
	
	/**
	 * 
	 * @Title: getStrategyByQuery  
	 * @Description: 查询用户策略
	 * @param userName 用户名
	 * @param pageSize 每面条数
	 * @param pageNo 第几页（从1开始）
	 * @return List<PkpmCloudStrategyVO>    返回用户策略信息
	 */
	List<PkpmCloudStrategyVO> getStrategyByQuery(String userName, int pageSize, int pageNo);
	
	

}

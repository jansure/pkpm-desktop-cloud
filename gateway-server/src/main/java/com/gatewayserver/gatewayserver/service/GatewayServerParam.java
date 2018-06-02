/**
 * 
 */
package com.gatewayserver.gatewayserver.service;

import java.util.List;
import java.util.Map;

import com.gateway.common.domain.PkpmAdDef;
import com.gateway.common.domain.PkpmProjectDef;

/**
 * @Description 供云平台订单使用Service
 * @author yangpengfei
 * @time 2018年4月12日 下午3:10:27
 */
public interface GatewayServerParam {

	/**
	 * 返回可分配的Project列表
	 * @param adIpAddress
	 * @param areaCode
	 * @author yangpengfei
	 * @time 2018年4月13日 上午10:20:01
	 */
	List<PkpmProjectDef> getProjectDefs(String adIpAddress, String areaCode); 
	
	/** 
	 * 返回可分配的AD列表
	 * @param adIpAddress
	 * @param areaCode
	 * @param adOu
	 * @param isValid
	 * @author yangpengfei
	 * @time 2018年4月13日 上午10:18:27
	 */
	List<PkpmAdDef> getAdDefs(String adIpAddress, String areaCode, String adOu, Integer isValid); 
	
	/**
	 * 分配adId和projectId
	 * @param areaCode 区域code
	 * @param ouName 组织名，默认pkpm
	 * @return adId和projectId
	 * @author yangpengfei
	 * @time 2018年4月12日 下午7:28:22
	 */
	Map<String, String> getAdAndProject(String areaCode, String ouName);
	
	/**
	 * 根据projectId、areaCode获取ProjectDef
	 * @param projectId
	 * @param areaCode
	 * @return PkpmProjectDef
	 * @author yangpengfei
	 * @date 2018/04/27
	 */
	PkpmProjectDef getProjectDef(String projectId, String areaCode);
}

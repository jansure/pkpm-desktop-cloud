package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品接口类
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface PkpmCloudComponentDefService {
	
	/**
	 * 根据产品类型id获取自动配置的components
	 * @param productType 父类id
	 * @return
	 */
    List<ComponentVO> getComponentListByProductType(Integer productType);
    
    /**
     * 返回购买配置项类型列表(如地域、软件名称、主机配置、云存储)
     * @return
     */
    List<Map<String, Object>> getComponentTypeList();
    
    /**
     * 根据配置项类型返回对应的所有配置项
     * @param componentType
     * @return
     */
    List<Map<String, Object>> getConfigByComponentType(Integer componentType);

    /**
	 * 
	 * @Title: getList  
	 * @Description: 获取所有列表
	 * @param @return    参数  
	 * @return List<ComponentVO>    返回类型  
	 * @throws
	 */
	List<PkpmCloudComponentDef> getList();

}

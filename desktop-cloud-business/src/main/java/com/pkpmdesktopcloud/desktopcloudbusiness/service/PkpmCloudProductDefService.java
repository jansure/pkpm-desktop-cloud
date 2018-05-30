package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品接口类
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface PkpmCloudProductDefService {
	/**
	 * 根据父id获取子目录
	 * @param productType 父类id
	 * @return
	 */
//	List<PkpmCloudProductDef> getProductByType(Integer productType);
	
    /**
     * 返回产品套餐类型列表，并指定默认选项
     * @return
     */
    List<PkpmCloudProductDef> getProductTypeList();
    
    /**
	 * 产品购买列表
	 * @return
	 */
	Map<String, Object> getProductBuyMap();

}

package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.Navigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ProductInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品映射接口
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface ProductDAO {
	/**
	 * 根据父id获取子目录
	 * @param productType 产品类型
	 * @return
	 */
    List<ProductInfo> getProductByType(Integer productType);
    
    /**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
    List<Navigation> getNavByPid(Integer parentNavId);
    
    /**
     * 根据key值获取对应的SysConfig实体
     * @param key
     * @return
     */
    SysConfig getSysConfig(String key);
    
    /**
     * 根据产品套餐类型获取对应的配置类别list
     * @param productType
     * @return
     */
    List<Integer> getCompTypeList(Integer productType);
    
    /**
     * 根据productId获取对应的product实体
     * @param productId
     * @return
     */
    List<ProductInfo> getProductByProductId(Integer productId);
    
    /**
	 * 根据产品类型id获取自动配置的components
	 * @param productType 产品套餐类型
	 * @param componentType 配置类型
	 * @return
	 */
    List<ComponentVO> getComponentByPid(Integer productType,  Integer componentType);
   
    /**
     * 返回全部产品套餐列表
     * @return
     */
    List<Map<String, Object>> getProductTypeList();
    
}

package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品映射接口
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
@Mapper
public interface PkpmCloudProductDefMapper {
	
	@Select("select * from pkpm_cloud_product_def (#{productInfo})")
	@Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudProductDef> getProductList(PkpmCloudProductDef productInfo);
	
	@Select("select * from pkpm_cloud_navigation (#{navigation})")
	@Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudNavigation> getNavigationList(PkpmCloudNavigation navigation);
	
	@Select("select * from pkpm_sys_config (#{sysConfig})")
	@Lang(SimpleSelectLangDriver.class)
	List<PkpmSysConfig> getSysConfigList(PkpmSysConfig sysConfig);
    
    /**
	 * 根据产品类型id获取自动配置的components
	 * @param productType 产品套餐类型
	 * @param componentType 配置类型
	 * @return
	 */
    List<ComponentVO> getComponentByPid(@Param("productType") Integer productType, @Param("componentType") Integer componentType);
    
    /**
     * 根据产品套餐类型获取对应的配置类别list
     * @param productType
     * @return
     */
    List<Integer> getCompTypeList(@Param("productType") Integer productType);
   
    
    @Select("select DISTINCT(product_type), product_id, product_desc from pkpm_cloud_product_def")
	@Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudProductDef> getProductTypeList();
    
    
    /**
     * 根据配置项类型返回对应的所有配置项
     * @param componentType
     * @return
     */
    List<Map<String, Object>> getConfigByComponentType(@Param("componentType") Integer componentType);
   
}

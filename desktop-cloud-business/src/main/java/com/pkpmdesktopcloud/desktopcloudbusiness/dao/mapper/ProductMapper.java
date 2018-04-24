package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
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
@Mapper
public interface ProductMapper {
	
	@Select("select * from pkpm_cloud_product_def (#{productInfo})")
	@Lang(SimpleSelectLangDriver.class)
    List<ProductInfo> getProductList(ProductInfo productInfo);
	
	@Select("select * from pkpm_cloud_navigation (#{navigation})")
	@Lang(SimpleSelectLangDriver.class)
    List<Navigation> getNavigationList(Navigation navigation);
	
	@Select("select * from pkpm_sys_config (#{sysConfig})")
	@Lang(SimpleSelectLangDriver.class)
	List<SysConfig> getSysConfigList(SysConfig sysConfig);
    
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
    /**
     * 返回全部产品套餐列表
     * @return
     */
    List<Map<String, Object>> getProductTypeList();
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
    List<Map<String, Object>> getConfigByComponentType(@Param("componentType") Integer componentType);
    /**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
    List<Map<String, String>> getClientInfo(@Param("userMobileNumber") String userMobileNumber, @Param("workId") Long workId);
}

package com.gateway.cloud.business.service;

import java.util.List;
import java.util.Map;

import com.gateway.cloud.business.entity.Navigation;
import com.gateway.cloud.business.entity.ProductInfo;
import com.gateway.cloud.business.vo.ComponentVO;

/**
 * 产品接口类
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface ProductService {
	/**
	 * 根据父id获取子目录
	 * @param productType 父类id
	 * @return
	 */
	List<ProductInfo> getProductByParentId(String productType);
	/**
     * 根据key值获取对应的value
     * @param key SysConfig的主键
     * @return
     */
	String getSysValue(String key);
	/**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
    List<Navigation> getNavByPid(Integer parentNavId);
	/**
	 * 根据产品类型id获取自动配置的components
	 * @param productType 父类id
	 * @return
	 */
    List<ComponentVO> getComponentByPid(String productType, String componentType);
    /**
     * 根据产品套餐类型获取对应的配置类别list
     * @param productType
     * @return
     */
    List<Integer> getCompTypeList(Integer productType);
    /**
     * 返回产品套餐类型列表
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
    List<Map<String, Object>> getConfigByComponentType(Integer componentType);
    /**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
    List<Map<String, String>> getClientInfo(String userMobileNumber, Long workId);

}

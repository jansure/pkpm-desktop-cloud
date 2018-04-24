package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ProductDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.ProductMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.Navigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ProductInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

@Repository
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * 根据父id获取子目录
	 * @param productType 父类id
	 * @return
	 */
	@Override
	public List<ProductInfo> getProductByType(Integer productType) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductType(productType);
		
		List<ProductInfo> list = productMapper.getProductList(productInfo );
		return list;
	}
    /**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
	@Override
	public List<Navigation> getNavByPid(Integer parentNavId){
		Navigation navigation = new Navigation();
		navigation.setParentNavId(parentNavId);
		
		List<Navigation> list = productMapper.getNavigationList(navigation );
		return list;
		
	}
    /**
     * 根据key值获取对应的SysConfig实体
     * @param key
     * @return
     */
	@Override
	public SysConfig getSysConfig(String key) {
		SysConfig sysConfig = new SysConfig();
		sysConfig.setKey(key);
		
		List<SysConfig> list = productMapper.getSysConfigList(sysConfig );
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
    
    /**
     * 根据productId获取对应的product实体
     * @param productId
     * @return
     */
	@Override
	public List<ProductInfo> getProductByProductId(Integer productId){
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId(productId);
		
		List<ProductInfo> list = productMapper.getProductList(productInfo );
		return list;
	}
    /**
	 * 根据产品类型id获取自动配置的components
	 * @param productType 产品套餐类型
	 * @param componentType 配置类型
	 * @return
	 */
	@Override
	public List<ComponentVO> getComponentByPid(Integer productType, Integer componentType){
		//多表查询
	}
	
	/**
     * 根据产品套餐类型获取对应的配置类别list
     * @param productType
     * @return
     */
	@Override
	public List<Integer> getCompTypeList(Integer productType){
		//多表查询
	}
    
    /**
     * 返回全部产品套餐列表
     * @return
     */
	@Override
	public List<Map<String, ProductInfo>> getProductTypeList(){
		ProductInfo productInfo = new ProductInfo();
		
		List<ProductInfo> list = productMapper.getProductList(productInfo );
		if(list != null && list.size() > 0) {
			Map<Integer, List<ProductInfo>> aa = list.stream().collect(Collectors.groupingBy(ProductInfo::getProductType));
		}
		return list;
	}
	
    /**
     * 返回购买配置项类型列表(如地域、软件名称、主机配置、云存储)
     * @return
     */
	@Override
	public List<Map<String, Object>> getComponentTypeList();
    /**
     * 根据配置项类型返回对应的所有配置项
     * @param componentType
     * @return
     */
	@Override
	public List<Map<String, Object>> getConfigByComponentType(@Param("componentType") Integer componentType);
    /**
     * 根据用户手机号及工单号查询用户云桌面开户信息
     * @param userMobileNumber
     * @param
     * @return
     */
	@Override
	public List<Map<String, String>> getClientInfo(@Param("userMobileNumber") String userMobileNumber, @Param("workId") Long workId);
	

}

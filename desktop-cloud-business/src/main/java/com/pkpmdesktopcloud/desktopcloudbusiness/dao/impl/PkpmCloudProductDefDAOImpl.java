package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmCloudProductDefMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

@Repository
public class PkpmCloudProductDefDAOImpl implements PkpmCloudProductDefDAO{
	
	@Autowired
	private PkpmCloudProductDefMapper productMapper;
	
	/**
	 * 根据父id获取子目录
	 * @param productType 父类id
	 * @return
	 */
	@Override
	public List<PkpmCloudProductDef> getProductByType(Integer productType) {
		PkpmCloudProductDef productInfo = new PkpmCloudProductDef();
		productInfo.setProductType(productType);
		
		List<PkpmCloudProductDef> list = productMapper.getProductList(productInfo );
		return list;
	}
	
    /**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
	@Override
	public List<PkpmCloudNavigation> getNavByPid(Integer parentNavId){
		PkpmCloudNavigation navigation = new PkpmCloudNavigation();
		navigation.setParentNavId(parentNavId);
		
		List<PkpmCloudNavigation> list = productMapper.getNavigationList(navigation );
		return list;
		
	}
    /**
     * 根据key值获取对应的SysConfig实体
     * @param key
     * @return
     */
	@Override
	public PkpmSysConfig getSysConfig(String key) {
		PkpmSysConfig sysConfig = new PkpmSysConfig();
		sysConfig.setKey(key);
		
		List<PkpmSysConfig> list = productMapper.getSysConfigList(sysConfig );
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
	public List<PkpmCloudProductDef> getProductByProductId(Integer productId){
		PkpmCloudProductDef productInfo = new PkpmCloudProductDef();
		productInfo.setProductId(productId);
		
		List<PkpmCloudProductDef> list = productMapper.getProductList(productInfo );
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
	public List<Map<String, Object>> getProductTypeList(){
		
		List<PkpmCloudProductDef> list = productMapper.getProductTypeList();
		if(list != null && list.size() > 0) {
			
			//obj2Map
			
			
		}
		return null;
	}
	  
}

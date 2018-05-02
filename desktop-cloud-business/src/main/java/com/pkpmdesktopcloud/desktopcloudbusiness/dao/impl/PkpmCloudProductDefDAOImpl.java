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

import javax.annotation.Resource;

@Repository
public class PkpmCloudProductDefDAOImpl implements PkpmCloudProductDefDAO{
	
	@Resource
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
     * 返回全部产品套餐列表
     * @return
     */
	@Override
	public List<PkpmCloudProductDef> getProductTypeList(){
		
		List<PkpmCloudProductDef> list = productMapper.getProductTypeList();
		
		return list;
	}
	  
}

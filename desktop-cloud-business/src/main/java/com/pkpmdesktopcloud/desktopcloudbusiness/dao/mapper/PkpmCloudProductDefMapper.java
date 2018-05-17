package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;

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
    
    @Select("select DISTINCT(product_type), product_id, product_desc from pkpm_cloud_product_def")
	@Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudProductDef> getProductTypeList();
    
}

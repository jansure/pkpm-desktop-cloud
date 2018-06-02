package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmCloudComponentDefMapper {

    @Select("select * from pkpm_cloud_component_def (#{componentInfo})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudComponentDef> select(PkpmCloudComponentDef componentInfo );
    
    @Insert("insert into pkpm_cloud_component_def (#{componentInfo})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "componentId", keyColumn = "component_id")
    Integer insert(PkpmCloudComponentDef componentInfo);

    @Update("update pkpm_cloud_component_def (#{componentInfo}) WHERE component_id = #{componentId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmCloudComponentDef componentInfo);
    
    @Select("SELECT prod.product_id product_id,prod.product_name product_name,prod.product_desc product_desc," +
    		" prod.component_id component_id,comp.component_name component_name," + 
    		" comp.component_type	component_type,comp.component_type_name component_type_name" + 
    		" FROM pkpm_cloud_product_def prod" + 
    		" LEFT JOIN pkpm_cloud_component_def comp ON prod.component_id=comp.component_id" + 
    		" WHERE prod.product_type = #{productType,jdbcType=INTEGER}")
    @Lang(SimpleSelectLangDriver.class)
    List<ComponentVO> getComponentListByProductType(@Param("productType")Integer productType);

}

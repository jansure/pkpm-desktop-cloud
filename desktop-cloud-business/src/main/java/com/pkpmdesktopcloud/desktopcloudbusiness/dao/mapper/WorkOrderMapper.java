package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;

@Mapper
public interface WorkOrderMapper {
	
	@Select("select * from pkpm_cloud_work_order (#{workOrder})")
    @Lang(SimpleSelectLangDriver.class)
	List<WorkOrder> findWorkOrderList(WorkOrder workOrder);
	
	@Insert("insert into pkpm_cloud_work_order (#{workOrder})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(WorkOrder workOrder);

    @Update("update pkpm_cloud_work_order (#{workOrder}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(WorkOrder workOrder);
}

package com.pkpmcloud.fileserver.dao.mapper;

import java.util.List;

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
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

@Mapper
public interface PkpmFileInfoMapper {
	
    @Select("select * from pkpm_file_info (#{fileInfo})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmFileInfo> select(PkpmFileInfo fileInfo );
    
    @Insert("insert into pkpm_file_info (#{fileInfo})")
    @Lang(SimpleInsertLangDriver.class)
    Integer insert(PkpmFileInfo fileInfo);

    @Update("update pkpm_file_info (#{fileInfo}) WHERE md5 = #{md5}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmFileInfo fileInfo);
   
    //@Select("select * from pkpm_file_info where origin_file_name like '%'#{fileInfo}'%'")
  //  @Select("select * from pkpm_file_info where origin_file_name like concat(concat('%',?),'%') ")
    @Select("select * from pkpm_file_info where origin_file_name like CONCAT('%','${fileName}','%') order by create_time desc")
	List<PkpmFileInfo> fileListByName(@Param("fileName") String  fileName);
    
    @Select("select * from pkpm_file_info order by create_time desc")
	List<PkpmFileInfo> fileList();

	  
    /**  
	 * @Title: countByName  
	 * @Description: 获取总条数
	 * @param @param fileName 文件名
	 * @return long    总条数
	 */ 
    @Select("select count(*) from pkpm_file_info where origin_file_name like CONCAT('%','${fileName}','%')")
	long countByName(@Param("fileName")String fileName);
	  
    /**  
	 * @Title: filePageListByName  
	 * @Description: 获取文件分页列表  
	 * @param @param fileName 文件名
	 * @param @param beginPos 开始
	 * @param @param pageSize 条数
	 * @return List<PkpmFileInfo>    文件分页列表  
	 * @throws  
	 */ 
    @Select("select * from pkpm_file_info where origin_file_name like CONCAT('%','${fileName}','%') order by create_time desc limit #{beginPos},#{pageSize} ")
	List<PkpmFileInfo> filePageListByName(@Param("fileName")String fileName, @Param("beginPos")Integer beginPos, @Param("pageSize")Integer pageSize);
    

}
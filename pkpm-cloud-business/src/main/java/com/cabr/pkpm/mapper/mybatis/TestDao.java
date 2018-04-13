/*package com.cabr.pkpm.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.io.ResolverUtil.Test;
import org.springframework.data.repository.query.Param;

@Mapper
public interface TestDao {
    @Select("select * from Test (#{test})")
    @Lang(SimpleSelectLangDriver.class)
    List<Test> select(Test test);

    @Insert("insert into Test (#{test})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(keyColumn = "id", useGeneratedKeys = true)
    Integer insert(Test test);

    @Update("update Test (#{test}) where name=#{name}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(Test test);

    @Delete("delete from Test where id=#{id}")
    Integer delete(@Param("id") Integer id);
    
    
     * 
     *  public Test selectById(@Nonnull Integer id)
    {
        Test test = new Test();
        test.setId(id);
        List<Test> list = mapper.select(test);
        if(CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
     
}
*/
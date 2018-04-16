package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmJobStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmJobStatusMapper {
    @Insert("insert into pkpm_job_status (#{pkpmJob})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "jobId", keyColumn = "job_id")
    Integer insert(PkpmJobStatus pkpmJob);

    @Select("select * from pkpm_job_status (#{pkpmJob}) limit (#{page}),(#{pageSize})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmJobStatus> select(PkpmJobStatus pkpmJob);

    @Update("update pkpm_job_status (#{pkpmJob}) WHERE job_id = #{jobId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmJobStatus pkpmJob);
}

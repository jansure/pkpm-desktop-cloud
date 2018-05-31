package com.pkpmdesktopcloud.desktopcloudsearch.dao.mapper;

import com.gateway.common.domain.PkpmOperatorStatus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface PkpmOperatorStatusMapper {

    @Select({"<script>",
            "SELECT * FROM tbl_order",
            "WHERE operator.is_finished=1",
            "<when test='title!=null'>",
            "AND mydate = #{mydate}",
            "</when>",
            "</script>"}

            "SELECT * FROM pkpm_operator_status as operator\n" +
            "WHERE operator.is_finished=1" +
            "AND operator.computer_name=#{pkpmOperatorStatus.get}" +
            "AND operator.create_time=\n" +
            "AND operator.finish_time=")
    @Results({
            @Result(id=true, column="id", property="id"),
            @Result(column="job_id", property="jobId"),
            @Result(column="project_id", property="projectId"),
            @Result(column="user_id", property="userId"),
            @Result(column="ad_id", property="adId"),
            @Result(column="create_time", property="createTime"),
            @Result(column="finishTime", property="finishTime")
    })
    List<PkpmOperatorStatus> select(PkpmOperatorStatus pkpmOperatorStatus);


}

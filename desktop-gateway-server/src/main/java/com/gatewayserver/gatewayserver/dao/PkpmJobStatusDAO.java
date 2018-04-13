package com.gatewayserver.gatewayserver.dao;

import com.gatewayserver.gatewayserver.domain.PkpmJobStatus;

import java.util.List;

/**
 * @Description:
 * @Author: yangpengfei
 * @Date: 2018/3/27
 */
public interface PkpmJobStatusDAO {
    // 通过jobId获取PkpmJobStatus信息
    PkpmJobStatus selectByJobId(String jobId);

    // 更新记录
    Integer update(PkpmJobStatus pkpmJob);

    // 插入数据
    Integer insert(PkpmJobStatus pkpmJob);

    //查询记录
    List<PkpmJobStatus> select(PkpmJobStatus pkpmJob);
    
    //查询分业记录
    List<PkpmJobStatus> selectByPage(PkpmJobStatus pkpmJob,int pageNo, int pageSize);
}
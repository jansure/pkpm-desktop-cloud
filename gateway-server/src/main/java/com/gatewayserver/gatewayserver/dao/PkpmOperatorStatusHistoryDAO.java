package com.gatewayserver.gatewayserver.dao;

import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatusHistory;

import java.util.List;

/**
 * @author wangxiulong
 * @ClassName: PkpmOperatorStatusHistoryDAO
 * @Description: 任务更新日志  Dao
 * @date 2018年4月10日
 */
public interface PkpmOperatorStatusHistoryDAO {

    //查询
    List<PkpmOperatorStatusHistory> select(PkpmOperatorStatusHistory operatorStatusHistory);

    // 更新记录
    Integer update(PkpmOperatorStatusHistory operatorStatusHistory);

    // 保存记录
    Integer save(PkpmOperatorStatusHistory operatorStatusHistory);
}

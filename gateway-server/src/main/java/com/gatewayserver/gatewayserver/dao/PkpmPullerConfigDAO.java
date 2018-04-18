package com.gatewayserver.gatewayserver.dao;

import java.util.List;

import com.gateway.common.domain.PkpmPullerConfig;

/**
 * @author wangxiulong
 * @ClassName: PkpmPullerConfigDAO
 * @Description: Puller配置Dao
 * @date 2018年4月8日
 */
public interface PkpmPullerConfigDAO {
    // 查询所有配置信息
    List<PkpmPullerConfig> selectAll();

    // 更新记录
    Integer update(PkpmPullerConfig pullerConfig);

    // 插入数据
    Integer insert(PkpmPullerConfig pullerConfig);
}

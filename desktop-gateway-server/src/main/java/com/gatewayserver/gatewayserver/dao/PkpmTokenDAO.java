package com.gatewayserver.gatewayserver.dao;

import com.gatewayserver.gatewayserver.domain.PkpmToken;

/**
 * @Description:
 * @Author: yangpengfei
 * @Date: 2018/3/23
 */
public interface PkpmTokenDAO {
    // 查询记录
    PkpmToken select(PkpmToken pkpmToken);

    // 更新记录
    Integer update(PkpmToken pkpmToken);

    // 插入数据
    Integer insert(PkpmToken pkpmToken);
}

package com.gatewayserver.gatewayserver.dao;

import com.gatewayserver.gatewayserver.domain.PkpmCloudUserInfo;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
public interface PkpmCloudUserInfoDAO {
    PkpmCloudUserInfo selectById(Integer userId);
}

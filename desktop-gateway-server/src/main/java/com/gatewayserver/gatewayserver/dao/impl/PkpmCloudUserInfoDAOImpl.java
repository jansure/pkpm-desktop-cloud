package com.gatewayserver.gatewayserver.dao.impl;

import com.gatewayserver.gatewayserver.dao.PkpmCloudUserInfoDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmCloudUserInfoMapper;
import com.gatewayserver.gatewayserver.domain.PkpmCloudUserInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
@Repository
public class PkpmCloudUserInfoDAOImpl implements PkpmCloudUserInfoDAO {
    @Resource
    private PkpmCloudUserInfoMapper mapper;

    @Override
    public PkpmCloudUserInfo selectById(Integer userId) {
        PkpmCloudUserInfo userInfo = new PkpmCloudUserInfo();
        userInfo.setUserId(userId);
        List<PkpmCloudUserInfo> userInfoList = mapper.select(userInfo);
        return userInfoList.get(0);
    }
}

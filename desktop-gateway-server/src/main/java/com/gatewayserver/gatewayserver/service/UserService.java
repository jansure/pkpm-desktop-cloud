package com.gatewayserver.gatewayserver.service;

import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.record.Records;
import com.gatewayserver.gatewayserver.dto.resp.userlist.UserList;

public interface UserService {

    /**
     * 查询 桌面 用户列表
     *
     * @param info
     * @return
     */

	UserList queryDesktopUserList(CommonRequestBean info);

    /**
     * 查询桌面用户登录记录
     *
     * @param info
     * @return
     */
	Records queryUserLoginRecord(CommonRequestBean info);

}

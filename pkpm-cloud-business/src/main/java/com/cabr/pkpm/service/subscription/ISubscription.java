package com.cabr.pkpm.service.subscription;

import java.util.List;
import java.util.Map;

import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.utils.ResponseResult;


public interface ISubscription {

	ResponseResult saveSubsDetails(UserInfo userInfo,WorkOrderVO wo);

}

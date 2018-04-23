package com.gateway.cloud.business.service;

import com.gateway.cloud.business.utils.ResponseResult;

public interface SubsDetailsService {
	//产品列表
	ResponseResult showList(int userId, Integer currentPage, Integer pageSize);

}

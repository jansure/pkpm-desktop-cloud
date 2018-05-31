package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;

public interface PkpmCloudSubsDetailsService {
	
	final String MY_PRODUCT_ID = "MyProduct";
	
	//产品列表
	PageBean<MyProduct> showList(int userId, Integer currentPage, Integer pageSize);

	PageBean<MyProduct> showListtest(Integer userID, Integer currentPage, Integer pageSize);
}

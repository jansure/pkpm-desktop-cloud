/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pkpm.pay.account.service;

import com.pkpm.pay.account.entity.RpAccount;
import com.pkpm.pay.common.core.page.PageBean;
import com.pkpm.pay.common.core.page.PageParam;

/**
 *  账户service接口
 * glory-cloud
 * @author：
 */
public interface RpAccountService{
	
	/**
	 * 保存
	 */
	void saveData(RpAccount rpAccount);

	/**
	 * 更新
	 */
	void updateData(RpAccount rpAccount);

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccount getDataById(String id);
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpAccount rpAccount);
	
	/**
	 * 根据userNo获取数据
	 * 
	 * @param userNo
	 * @return
	 */
	RpAccount getDataByUserNo(String userNo);
	
}
package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudNavigationDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudNavigationService;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class PkpmCloudNavigationServiceImpl implements PkpmCloudNavigationService {
	
	@Autowired
	private PkpmCloudNavigationDAO pkpmCloudNavigationDAO;
	
	@Override
	public List<PkpmCloudNavigation> getNavByParentId(Integer parentNavId) {
		List<PkpmCloudNavigation> list = pkpmCloudNavigationDAO.getNavByParentId(parentNavId);
		return list;
	}

}

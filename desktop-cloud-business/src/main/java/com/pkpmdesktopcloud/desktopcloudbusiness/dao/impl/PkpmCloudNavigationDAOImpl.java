package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudNavigationDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmCloudNavigationMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;

@Repository
public class PkpmCloudNavigationDAOImpl implements PkpmCloudNavigationDAO{
	
	@Autowired
	private PkpmCloudNavigationMapper pkpmCloudNavigationMapper;
	
	
    /**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
	@Override
	public List<PkpmCloudNavigation> getNavByParentId(Integer parentNavId){
		PkpmCloudNavigation navigation = new PkpmCloudNavigation();
		navigation.setParentNavId(parentNavId);
		
		List<PkpmCloudNavigation> list = pkpmCloudNavigationMapper.select(navigation );
		return list;
		
	}
	  
}

package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudNavigationDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudNavigationService;
import com.pkpmdesktopcloud.redis.RedisCache;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
@Slf4j
public class PkpmCloudNavigationServiceImpl implements PkpmCloudNavigationService {
	
	private static final String ALL_NAVIGATION_ID = "allNavigation";
	private static List<PkpmCloudNavigation> childNav = new ArrayList<PkpmCloudNavigation>();  
	
	@Resource
	private PkpmCloudNavigationDAO pkpmCloudNavigationDAO;
	
//	@Override
//	public List<PkpmCloudNavigation> getNavByParentId(Integer parentNavId) {
//		RedisCache cache = new RedisCache(ALL_NAVIGATION_ID);
//		List<PkpmCloudNavigation> listNav = (List<PkpmCloudNavigation>)cache.getObject("all");
//		
//		// 若存在Redis缓存，从缓存中读取
//		if (listNav != null) {
//			return listNav;
//		}
//		
//		// 若不存在对应的Redis缓存，从数据库查询
//		listNav = pkpmCloudNavigationDAO.getNavByParentId(SysConstant.NAVIGATION_ID);
//		// 写入Redis缓存
//		cache.putObject("all", listNav);
//		return listNav;
//	}

	@Override
	public List<PkpmCloudNavigation> getNavTree() {
		RedisCache cache = new RedisCache(ALL_NAVIGATION_ID);
		List<PkpmCloudNavigation> navTree = (List<PkpmCloudNavigation>)cache.getObject("all");
		
		// 若存在Redis缓存，从缓存中读取
		if (navTree != null) {
			return navTree;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		List<PkpmCloudNavigation> navigation = pkpmCloudNavigationDAO.getNavigation();
		if (null != navigation && !navigation.isEmpty()) {
			navTree = this.buildByRecursive(navigation);
		}
		
		// 写入Redis缓存
		cache.putObject("all", navTree);
		return navTree;
	}
 
	/** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    public PkpmCloudNavigation findChildren(PkpmCloudNavigation treeNode,List<PkpmCloudNavigation> treeNodes) {  
        for (PkpmCloudNavigation it : treeNodes) {  
            if(treeNode.getNavId().equals(it.getParentNavId())) {  
                if (treeNode.getChildren() == null) {  
                    treeNode.setChildren(new ArrayList<PkpmCloudNavigation>());  
                }  
                treeNode.getChildren().add(findChildren(it,treeNodes));  
            }  
        }  
        return treeNode;  
    }  
    /** 
     * 使用递归方法建树 
     * @param treeNodes 
     * @return 
     */  
    public List<PkpmCloudNavigation> buildByRecursive(List<PkpmCloudNavigation> treeNodes) {  
        List<PkpmCloudNavigation> trees = new ArrayList<PkpmCloudNavigation>();  
        for (PkpmCloudNavigation treeNode : treeNodes) {  
            if (SysConstant.NAVIGATION_ID == treeNode.getParentNavId()) {  
                trees.add(findChildren(treeNode,treeNodes));  
            }  
        }  
        return trees;  
    }  

}

package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

/**
 * 产品映射接口
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public interface PkpmCloudNavigationDAO {
	
    /**
     * 获取导航子目录并控制层级
     * @param parentNavId
     * @return
     */
    List<PkpmCloudNavigation> getNavByParentId(Integer parentNavId);
    
    /**
     * 获取全部导航
     * @date 2018/5/31
     * @return
     */
    List<PkpmCloudNavigation> getNavigation();
    
}

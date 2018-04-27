package com.gatewayserver.gatewayserver.dao;


import java.util.List;

import com.gateway.common.domain.PkpmProjectDef;
/**
 * @Description 项目信息DAO
 * @author yangpengfei
 * @time 2018年4月12日 下午3:21:31
 */
public interface PkpmProjectDefDAO {
	
    PkpmProjectDef selectById(String projectId);
    
    /**
     * @Description 查询项目列表
     * @param pkpmProjectDef
     * @return PkpmProjectDef
     * @author yangpengfei
     * @time 2018年4月13日 上午9:24:23
     */
    List<PkpmProjectDef> select(PkpmProjectDef pkpmProjectDef);
    
    /**
	 * 根据projectId、areaCode获取ProjectDef
	 * @param projectId
	 * @param areaCode
	 * @return PkpmProjectDef
	 * @author yangpengfei
	 * @date 2018/04/27
	 */
    PkpmProjectDef selectByProjectIdAndAreaCode(String projectId, String areaCode);
}

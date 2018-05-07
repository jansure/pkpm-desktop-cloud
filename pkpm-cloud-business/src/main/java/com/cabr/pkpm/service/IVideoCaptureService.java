package com.cabr.pkpm.service;

import java.util.List;

import com.cabr.pkpm.entity.PkpmCloudVideoCapture;

/**
 * 
 * @ClassName: IVideoCaptureService  
 * @Description: 视频截图Service
 * @author wangxiulong  
 * @date 2018年5月7日  
 *
 */
public interface IVideoCaptureService {

	/**
	 * 
	 * @Title: insert  
	 * @Description: 插入数据
	 * @param record 视频截图Bean
	 * @return int    插入行数
	 * @throws
	 */
    int insert(PkpmCloudVideoCapture record);

    /**
     * 
     * @Title: selectByPrimaryKey  
     * @Description: 通过主键获取唯一一条数据 
     * @param capId 主键
     * @return PkpmCloudVideoCapture    返回视频截图
     * @throws
     */
    PkpmCloudVideoCapture selectByPrimaryKey(Integer capId);

    /**
     * 
     * @Title: selectAll  
     * @Description: 获取所有数据 
     * @return List<PkpmCloudVideoCapture>    返回视频截图集合
     * @throws
     */
    List<PkpmCloudVideoCapture> selectAll();
    
    /**
     * 
     * @Title: initData  
     * @Description: 初始化数据  
     * @return boolean    返回是否完成
     * @throws
     */
    boolean initData() ;
    
}
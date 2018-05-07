package com.cabr.pkpm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cabr.pkpm.entity.PkpmCloudVideoCapture;

@Mapper
public interface PkpmCloudVideoCaptureMapper {
    int deleteByPrimaryKey(Integer capId);

    int insert(PkpmCloudVideoCapture record);

    PkpmCloudVideoCapture selectByPrimaryKey(Integer capId);

    List<PkpmCloudVideoCapture> selectAll();

    int updateByPrimaryKey(PkpmCloudVideoCapture record);
}
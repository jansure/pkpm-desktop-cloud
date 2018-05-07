package com.idserver.dao;

import com.idserver.model.Identify;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@Mapper
public interface IdentifyDAO {


    Integer insert(Identify identify);

    Integer count(Identify identify);
}

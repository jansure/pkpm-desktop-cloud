package com.pkpmcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Component
@Mapper
public interface WhiteListDAO {
    Set<String> listProjectWhiteList(String projectId);
}

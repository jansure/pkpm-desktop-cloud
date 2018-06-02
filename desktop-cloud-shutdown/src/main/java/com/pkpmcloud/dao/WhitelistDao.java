package com.pkpmcloud.dao;

import com.pkpmcloud.model.ComputerDef;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
public interface WhitelistDao {
    Set<String> listComputersInWhitelist(String projectId);
}

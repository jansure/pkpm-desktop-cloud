package com.pkpmcloud.dao.impl;

import com.pkpmcloud.dao.WhitelistDao;
import com.pkpmcloud.dao.mapper.WhitelistMapper;
import com.pkpmcloud.model.ComputerDef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@Repository
public class WhitelistDaoImpl implements WhitelistDao {

    @Resource
    WhitelistMapper mapper;

    @Override
    public Set<String> listComputersInWhitelist(String projectId) {
        ComputerDef computerDef = new ComputerDef();
        computerDef.setProjectId(projectId);
        Set<ComputerDef> computerDefSet = mapper.listComputer(computerDef);
        Set<String> res = new HashSet<>();
        for (ComputerDef computer : computerDefSet) {
          res.add(computer.getComputerName()) ;
        }
        return res;
    }
}

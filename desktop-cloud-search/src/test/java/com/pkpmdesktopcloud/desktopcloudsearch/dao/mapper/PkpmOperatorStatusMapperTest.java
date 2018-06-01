package com.pkpmdesktopcloud.desktopcloudsearch.dao.mapper;

import com.gateway.common.domain.PkpmOperatorStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class PkpmOperatorStatusMapperTest {

    @Autowired
    PkpmOperatorStatusMapper mapper;

    @Test
    public void select() {
        PkpmOperatorStatus status = new PkpmOperatorStatus();
        status.setComputerName("GL-1707666680");
        LocalDateTime dateTime = LocalDateTime.parse("2018-04-04 16:38:34");
        List<PkpmOperatorStatus> pkpmOperatorStatusList=mapper.select(status);
        System.out.println(pkpmOperatorStatusList.size());
        System.out.println(pkpmOperatorStatusList);
    }
}
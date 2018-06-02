package com.pkpmcloud.fileserver.protocol.tracker;

import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.testbase.GetTrackerConnection;
import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.testbase.GetTrackerConnection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:13 <br/>
 */
public class GetGroupListCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetGroupListCommand command = new GetGroupListCommand();
            List<GroupState> list = command.execute(connection);
            for (GroupState groupState : list) {
                logger.debug(groupState.toString());
            }
        } finally {
            connection.close();
        }
    }
}

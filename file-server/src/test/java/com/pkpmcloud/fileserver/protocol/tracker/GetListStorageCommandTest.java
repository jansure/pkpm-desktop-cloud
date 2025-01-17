package com.pkpmcloud.fileserver.protocol.tracker;

import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.testbase.GetTrackerConnection;
import com.pkpmcloud.fileserver.conn.Connection;
import com.pkpmcloud.fileserver.model.StorageState;
import com.pkpmcloud.fileserver.testbase.GetTrackerConnection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:51 <br/>
 */
public class GetListStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetListStorageCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetStorageListCommand command = new GetStorageListCommand("group1");
            List<StorageState> storageStates = command.execute(connection);
            for (StorageState storageState : storageStates) {
                logger.debug(storageState.toString());
            }
        } finally {
            connection.close();
        }
    }
}

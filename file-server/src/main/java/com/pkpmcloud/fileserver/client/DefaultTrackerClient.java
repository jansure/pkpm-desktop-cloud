package com.pkpmcloud.fileserver.client;

import com.pkpmcloud.fileserver.conn.CommandExecutor;
import com.pkpmcloud.fileserver.exception.FastDfsException;
import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorageNodeInfo;
import com.pkpmcloud.fileserver.model.StorageState;
import com.pkpmcloud.fileserver.protocol.tracker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * 目录服务(Tracker)客户端接口 默认实现<br/>
 * <b>注意: 当前类最好使用单例，一个应用只需要一个实例</b>
 * 创建时间：2016/11/21 16:26 <br/>
 */
@Component
public class DefaultTrackerClient implements TrackerClient {
	
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClient.class);

    @Resource
    private CommandExecutor commandExecutor;
    
    public DefaultTrackerClient(){
    	
    }

    public DefaultTrackerClient(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public StorageNode getStorageNode() {
        GetStorageNodeCommand command = new GetStorageNodeCommand();
        return commandExecutor.execute(command);
    }

    @Override
    public StorageNode getStorageNode(String groupName) {
        StorageNode storageNode = null;
        GetStorageNodeCommand command = new GetStorageNodeCommand(groupName);
        try {
            storageNode = commandExecutor.execute(command);
        } catch (FastDfsException e) {
            logger.error("存储节点不存在 groupName=[" + groupName + "]", e);
        }
        return storageNode;
    }

    @Override
    public StorageNodeInfo getFetchStorage(String groupName, String filename) {
        GetFetchStorageCommand command = new GetFetchStorageCommand(groupName, filename, false);
        return commandExecutor.execute(command);
    }

    @Override
    public StorageNodeInfo getFetchStorageAndUpdate(String groupName, String filename) {
        GetFetchStorageCommand command = new GetFetchStorageCommand(groupName, filename, true);
        return commandExecutor.execute(command);
    }

    @Override
    public List<GroupState> getGroupStates() {
        GetGroupListCommand command = new GetGroupListCommand();
        List<GroupState> result = commandExecutor.execute(command);
        return result != null ? result : new ArrayList<GroupState>();
    }

    @Override
    public List<StorageState> getStorageStates(String groupName) {
        GetStorageListCommand command = new GetStorageListCommand(groupName);
        List<StorageState> result = commandExecutor.execute(command);
        return result != null ? result : new ArrayList<StorageState>();
    }

    @Override
    public StorageState getStorageState(String groupName, String storageIp) {
        GetStorageListCommand command = new GetStorageListCommand(groupName, storageIp);
        List<StorageState> result = commandExecutor.execute(command);
        if (result != null && result.size() > 1) {
            logger.warn("应该返回一条数据, 但是现在返回了{}条, 只取第一条", result.size());
        }
        return (result != null && result.size() >= 1) ? result.get(0) : null;
    }

    @Override
    public boolean deleteStorage(String groupName, String storageIp) {
        DeleteStorageCommand command = new DeleteStorageCommand(groupName, storageIp);
        try {
            commandExecutor.execute(command);
        } catch (Throwable e) {
            logger.error("踢出存储服务器失败, groupName=[" + groupName + "], storageIp=[" + storageIp + "]", e);
            return false;
        }
        return true;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}

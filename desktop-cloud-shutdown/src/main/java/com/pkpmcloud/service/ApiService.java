package com.pkpmcloud.service;

import com.pkpmcloud.model.Project;
import org.springframework.stereotype.Service;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Service
public interface ApiService {
    //自动关机脚本
    void invokeDesktopShutdownShell(Project project);
}

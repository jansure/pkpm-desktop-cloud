package com.pkpmcloud.thread;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.StringUtil;
import com.desktop.utils.exception.Exceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.constants.RequestConst;
import com.pkpmcloud.constants.ThreadConst;
import com.pkpmcloud.dao.WhitelistDao;
import com.pkpmcloud.model.BootProperty;
import com.pkpmcloud.model.Desktop;
import com.pkpmcloud.model.DesktopRootBean;
import com.pkpmcloud.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Component
@Slf4j
public class ShutdownMainThread  {



    private String token;
    private String projectId;
    private String areaName;
    private String adminName;
    private String adminPassword;
    private String projectDesc;
    private String workspaceUrlPrefix;

    private BootProperty bootProperty;


    @Resource
    private WhitelistDao whiteListDao;

    public ShutdownMainThread() {
    }

    //强制project信息赋值
    private void init(Project project,BootProperty bootProperty) {
        projectId = project.getProjectId();
        projectDesc=project.getProjectDesc();
        areaName = project.getAreaName();
        adminName = project.getAdminName();
        adminPassword = project.getAdminPassword();
        this.bootProperty=bootProperty;

        workspaceUrlPrefix = ApiConst.WORKSPACE_PREFIX;
        workspaceUrlPrefix = workspaceUrlPrefix.replace("{areaName}", areaName).replace("{projectId}", projectId);
        setToken();
        log.info("====>>项目启动：项目信息 {} {}/{} {}", project.getProjectDesc(), adminName, areaName, projectId);
    }

    public void invokeDesktopShutdownShell(Project project,BootProperty bootProperty) {
        init(project,bootProperty);
        List<Desktop> desktops = listActiveDesktop();
        Set<String> whiteList = whiteListDao.listComputersInWhitelist(projectId);
        if (null==desktops||desktops.size()==0){
            log.info("###{}项目无开机桌面",projectDesc);
            return;
        }
        Preconditions.checkNotNull(whiteList);
        int nThreads =desktops.size()/bootProperty.getTaskPerThread();
        nThreads = desktops.size()%bootProperty.getTaskPerThread()==0?nThreads:nThreads+1;
        log.info("###{} 当前：开机桌面{}台,启用线程{} 分配任务{}/线程",projectDesc,bootProperty.getTaskPerThread(),desktops.size(),nThreads);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-%d").build();
        ExecutorService service = new ThreadPoolExecutor(bootProperty.getCorePoolSize()
                ,bootProperty.getMaximumPoolSize(),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                namedThreadFactory);

        int fromIndex =0;
        int toIndex=ThreadConst.REQUEST_PER_THREAD;
        for (int i=0;i<nThreads;i++) {
            toIndex = Math.min(toIndex, desktops.size());
            List<Desktop> threadDesktopList = desktops.subList(fromIndex, toIndex);
            fromIndex+=ThreadConst.REQUEST_PER_THREAD;
            toIndex+=ThreadConst.REQUEST_PER_THREAD;
            ShutdownWorkerThread workThread =new ShutdownWorkerThread(token,projectDesc,workspaceUrlPrefix,whiteList,threadDesktopList,bootProperty);
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
            service.execute(workThread);

        }

    }


    private String setToken() {
        //token置为空
        token = null;
        //拼装url及请求体
        String url = ApiConst.TOKEN.replace("{areaName}", areaName);
        String authJson = RequestConst.authJson
                .replaceAll("\\{adminName\\}", adminName)
                .replaceAll("\\{adminPassword\\}", adminPassword)
                .replaceAll("\\{projectId\\}", projectId);
        //发送请求 获取Token;
        try {
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, authJson, token, 5, null, bootProperty.getHttpConnectionTimeout()).method(HttpMethods.POST));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 Created(201)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_CREATED == statusCode, "token请求失败，返回状态码" + statusCode);
            //获取Token，写入成员变量token，用于其他函数使用
            Map<String, Object> respHeaders = myHttpResponse.getHeaders();
            String respToken = respHeaders.get("X-Subject-Token").toString();
            Preconditions.checkNotNull(respToken, "token值为空");
            token = respToken;
        } catch (HttpProcessException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        }
        return token;
    }


    private List<Desktop> listActiveDesktop() {
        String url = workspaceUrlPrefix + ApiConst.LIST_ACTIVE_DESKTOP;
        
        try {
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, bootProperty.getHttpConnectionTimeout()).method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 OK(200)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_OK == statusCode, "开机桌面列表请求失败，返回状态码" + statusCode);

            //body反序列化为桌面列表
            ObjectMapper mapper = new ObjectMapper();
            DesktopRootBean rootBean = mapper.readValue(myHttpResponse.getBody(), DesktopRootBean.class);
            return rootBean.getDesktops();
        } catch (HttpProcessException | IOException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        }
    }




}

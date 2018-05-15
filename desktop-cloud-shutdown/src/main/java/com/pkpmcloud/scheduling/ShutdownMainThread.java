package com.pkpmcloud.scheduling;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.exception.Exceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.constants.RequestConst;
import com.pkpmcloud.constants.ThreadConst;
import com.pkpmcloud.dao.WhitelistDao;
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

    private String workspaceUrlPrefix;


    @Resource
    private WhitelistDao whiteListDao;

    public ShutdownMainThread() {
    }

    //强制project信息赋值
    private void init(Project project) {
        projectId = project.getProjectId();
        areaName = project.getAreaName();
        adminName = project.getAdminName();
        adminPassword = project.getAdminPassword();
        workspaceUrlPrefix = ApiConst.WORKSPACE_PREFIX;
        workspaceUrlPrefix = workspaceUrlPrefix.replace("{areaName}", areaName).replace("{projectId}", projectId);
        setToken();
        log.info("====>>项目启动：项目信息{}/{} {}/{}", adminName, project.getAreaDesc(), projectId, areaName);
    }

    public void invokeDesktopShutdownShell(Project project) throws ExecutionException, InterruptedException {
        init(project);
        List<Desktop> desktops = listActiveDesktop();
        log.info(desktops+"");
        Set<String> whiteList = whiteListDao.listComputersInWhitelist(projectId);
        Preconditions.checkNotNull(desktops);
        Preconditions.checkNotNull(whiteList);
        if (desktops.size() == 0) {
            log.info("无开机桌面");
            return;
        }
        int nThreads =desktops.size()/ThreadConst.REQUEST_PER_THREAD;
        nThreads = desktops.size()%ThreadConst.REQUEST_PER_THREAD==0?nThreads:nThreads+1;
        log.info("当前状态：开机桌面{}台,线程数{} 处理任务{}/线程",desktops.size(),nThreads,ThreadConst.REQUEST_PER_THREAD);
        ExecutorService service = Executors.newFixedThreadPool(nThreads);

        int fromIndex =0;
        int toIndex=ThreadConst.REQUEST_PER_THREAD;
        for (int i=0;i<nThreads;i++) {
            toIndex = Math.min(toIndex, desktops.size());
            List<Desktop> threadDesktopList = desktops.subList(fromIndex, toIndex);
            log.info("切分{}至{}--{}",fromIndex,toIndex,threadDesktopList.size());
            fromIndex+=ThreadConst.REQUEST_PER_THREAD;
            toIndex+=ThreadConst.REQUEST_PER_THREAD;
            ShutdownWorkThread workThread =new ShutdownWorkThread(token,workspaceUrlPrefix,whiteList,threadDesktopList);
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
            service.execute(workThread);
            /*service.execute(workThread);
            System.out.println(future.get());*/
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
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, authJson, token, 5, null, 10000).method(HttpMethods.POST));
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
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, 10000).method(HttpMethods.GET));
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

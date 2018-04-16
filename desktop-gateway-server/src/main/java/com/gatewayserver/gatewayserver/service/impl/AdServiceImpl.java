package com.gatewayserver.gatewayserver.service.impl;


import com.alibaba.fastjson.JSON;
import com.desktop.constant.AdConstant;
import com.desktop.constant.JobStatusEnum;
import com.desktop.constant.OperatoreTypeEnum;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.BeanUtil;
import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.dao.PkpmAdDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.domain.PkpmAdDef;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.dto.ad.AdComputer;
import com.gatewayserver.gatewayserver.dto.ad.AdUser;
import com.gatewayserver.gatewayserver.service.AdService;
import com.gatewayserver.gatewayserver.utils.AdUtil;
import com.gatewayserver.gatewayserver.utils.PkpmOperatorStatusBeanUtil;
import com.google.common.base.Preconditions;
import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdServiceImpl implements AdService {

    private static Map<Integer, LDAPConnectionPool> poolMap = new HashMap();
    @Resource
    private PkpmAdDefDAO pkpmAdDefDAO;
    @Resource
    private PkpmOperatorStatusDAO operatorStatusDAO;

    public static LDAPConnectionPool getConnectionPool(PkpmAdDef adDef) {
        //fixme 连接池管理加入netty，并且在应用启动的时候创建连接池
        String adIpAddress = adDef.getAdIpAddress();
        //SSL安全认证
        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
        SSLSocketFactory sslSocketFactory;

        LDAPConnectionPool connectionPool = null;
        //检测ip对应AD域连接池是否存在，不存在创建，存在则返回已有
        if (poolMap.get(adIpAddress) == null)
            try {
                sslSocketFactory = sslUtil.createSSLSocketFactory();
                String adminDN = AdUtil.getAdminDN(adDef);
                LDAPConnection connection = new LDAPConnection(sslSocketFactory, adIpAddress, adDef.getAdPort(), adminDN, adDef.getAdAdminPassword());
                connectionPool = new LDAPConnectionPool(connection, adDef.getAdMaxPoolCount());
                connectionPool.setConnectionPoolName(adIpAddress);
                log.info("AD连接池新建成功 --IP={} --MaxCon={}", connectionPool.getConnectionPoolName(), connectionPool.getMaximumAvailableConnections());
                return connectionPool;
            } catch (GeneralSecurityException e) {
                log.error("安全认证错误：" + e.getMessage());
            } catch (LDAPException e) {
                log.error("连接信息有误：" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            connectionPool = poolMap.get(adIpAddress);
            log.info("使用连接池{} --CurrentCon={}, --MaxConn={}", connectionPool.getConnectionPoolName(),
                    connectionPool.getCurrentAvailableConnections(),
                    connectionPool.getMaximumAvailableConnections());
            return connectionPool;
        }
        throw Exceptions.newBusinessException("连接池获取失败");
    }


    //获取AdDef
    public  PkpmAdDef getAdDefByAdId(Integer adId) {

        AdUtil.checkAdId(adId);
        PkpmAdDef adDef = this.pkpmAdDefDAO.selectById(adId);
        AdUtil.checkAdDef(adDef);
        return adDef;
    }

    //获取AdDef
    public  PkpmAdDef getAdDefByAdIpAddress(String adIpAddress) {
        Preconditions.checkNotNull(adIpAddress);
        PkpmAdDef adDef = this.pkpmAdDefDAO.selectByAdIpAddress(adIpAddress);
        AdUtil.checkAdDef(adDef);
        return adDef;
    }

    /**
     * @param requestBean
     * @return com.desktop.utils.page.ResultObject
     * @throws LDAPException
     * @Description 向AD域添加用户
     * @Author xuhe
     */

    public String addAdUser(CommonRequestBean requestBean) {

        //初始化插入数据
        Preconditions.checkNotNull(requestBean);
        PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
        BeanUtil.copyPropertiesIgnoreNull(requestBean, operatorStatus);
        operatorStatus.setStatus(JobStatusEnum.AD_CREATE.toString());
        operatorStatus.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
        PkpmOperatorStatusBeanUtil.checkNotNull(operatorStatus);

        //像数据库插入一条记录
        int result = operatorStatusDAO.save(operatorStatus);
        Preconditions.checkArgument(result == 1 && operatorStatus.getId() != null, "AD插入数据库初始化失败");
        log.info("AD插入数据库成功 --id={}", operatorStatus.getId());

        //获取AD连接信息，从连接池取出连接
        AdUtil.checkAdUser(requestBean);
        Integer adId = requestBean.getAdId();
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        Preconditions.checkNotNull(connectionPool, "AD获取连接池失败");


        LDAPConnection connection = null;
        try {

            connection = connectionPool.getConnection();
            Preconditions.checkState(connection != null && connection.isConnected(), "AD获取连接失败");

            //用户属性:获取相关用户信息
            String userName = requestBean.getUserName();
            String ouName = adDef.getAdOu();
            //AD属性:获取组织ouDN;
            String ouDN = AdUtil.getOuDN(adDef);

            String entryDN = String.format("CN=%s,%s", userName, ouDN);
            SearchResultEntry entry = connection.getEntry(entryDN);
            //检测到用户存在，返回成功
            if (entry != null) {
                operatorStatus.setStatus("AD_SUCCESS");
                operatorStatusDAO.update(operatorStatus);
                log.info("AD数据库记录状态更新成功 --id={}",operatorStatus.getId());
                requestBean.setOperatorStatusId(operatorStatus.getId());
                return userName + "已添加至AD域中";
            }
            // 不存在则创建
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("objectClass", "user"));
            attributes.add(new Attribute("samAccountName", userName));
           /* attributes.add(new Attribute("telephoneNumber", requestBean.getUserMobileNumber()));
            attributes.add(new Attribute("mail", requestBean.getUserEmail()));*/

            LDAPResult addResult = connection.add(entryDN, attributes);
            String addResultCodeName = addResult.getResultCode().getName();

            ArrayList<Modification> mods = new ArrayList<>();
            String password = requestBean.getUserLoginPassword();
            byte[] unicodePwd = ("\"" + password + "\"").getBytes("utf-16LE");
            mods.add(new Modification(ModificationType.REPLACE, "unicodePwd", unicodePwd));
            mods.add(new Modification(ModificationType.REPLACE, "userAccountControl",
                    Integer.toString(AdConstant.UF_NORMAL_ACCOUNT + AdConstant.UF_PASSWORD_EXPIRED)));

            LDAPResult modResult = connection.modify(entryDN, mods);
            String modResultCodeName = modResult.getResultCode().getName();
            if (addResultCodeName.equals("success") && modResultCodeName.equals("success")) {
                requestBean.setOuName(ouName);
                operatorStatus.setStatus(JobStatusEnum.AD_SUCCESS.toString());
                operatorStatusDAO.update(operatorStatus);
                log.info("AD数据库记录状态更新成功 --id={}",operatorStatus.getId());
                requestBean.setOperatorStatusId(operatorStatus.getId());
                return userName + "已新增至AD域中";
            }
        } catch (LDAPException | IOException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        operatorStatus.setStatus(JobStatusEnum.AD_FAILED.toString());
        operatorStatusDAO.update(operatorStatus);
        throw Exceptions.newBusinessException("用户{" + requestBean.getUserName() + "}创建失败");
    }



    public String updateAdUser(CommonRequestBean requestBean) {

        //初始化插入数据
        Preconditions.checkNotNull(requestBean);
        PkpmOperatorStatus operatorStatus = new PkpmOperatorStatus();
        BeanUtil.copyPropertiesIgnoreNull(requestBean, operatorStatus);
        operatorStatus.setStatus(JobStatusEnum.AD_CREATE.toString());
        operatorStatus.setOperatorType(OperatoreTypeEnum.DESKTOP.toString());
        PkpmOperatorStatusBeanUtil.checkNotNull(operatorStatus);

        //像数据库插入一条记录
        int result = operatorStatusDAO.save(operatorStatus);
        Preconditions.checkArgument(result == 1 && operatorStatus.getId() != null, "AD插入数据库初始化失败");
        log.info("AD插入数据库成功 --id={}", operatorStatus.getId());

        //获取AD连接信息，从连接池取出连接
        AdUtil.checkAdUser(requestBean);
        Integer adId = requestBean.getAdId();
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        Preconditions.checkNotNull(connectionPool, "AD获取连接池失败");

        LDAPConnection connection = null;
        try {

            connection = connectionPool.getConnection();
            Preconditions.checkState(connection != null && connection.isConnected(), "AD获取连接失败");
            //用户属性:获取相关用户信息
            String userName = requestBean.getUserName();
            //AD属性:获取组织ouDN;
            String ouDN = AdUtil.getOuDN(adDef);

            String entryDN = String.format("CN=%s,%s", userName, ouDN);
            log.info(entryDN);
            SearchResultEntry entry = connection.getEntry(entryDN);
            //检测到用户存在，返回成功
            if (entry == null)
                throw Exceptions.newBusinessException("用户不存在");
            ArrayList<Modification> mods = new ArrayList<>();
            String password = requestBean.getUserLoginPassword();
            byte[] unicodePwd = ("\"" + password + "\"").getBytes("utf-16LE");
            mods.add(new Modification(ModificationType.REPLACE, "unicodePwd", unicodePwd));
            /*mods.add(new Modification(ModificationType.REPLACE, "telephoneNumber", requestBean.getUserMobileNumber()));
            mods.add(new Modification(ModificationType.REPLACE, "mail", requestBean.getUserEmail()));*/
            mods.add(new Modification(ModificationType.REPLACE, "userAccountControl",
                    Integer.toString(AdConstant.UF_NORMAL_ACCOUNT + AdConstant.UF_PASSWORD_EXPIRED)));

            LDAPResult modResult = connection.modify(entryDN, mods);
            String modResultCodeName = modResult.getResultCode().getName();
            if (modResultCodeName.equals("success")){
                operatorStatus.setStatus(JobStatusEnum.AD_SUCCESS.toString());
                operatorStatusDAO.update(operatorStatus);
                log.info("AD数据库记录状态更新成功 --id={}",operatorStatus.getId());
                return userName+"密码修改成功";
            }

        } catch (LDAPException | IOException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("用户密码修改失败");
    }

    public int getUserCountByAdIpAddress(String adIpAddress) {

        PkpmAdDef adDef = getAdDefByAdIpAddress(adIpAddress);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);

        LDAPConnection connection = null;
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection);
            //AD属性:获取组织ouDN;
            String baseDN = AdUtil.getBaseDN(adDef);
            SearchRequest request = new SearchRequest(baseDN,
                    SearchScope.SUB, "objectCategory=CN=Person,CN=Schema,CN=Configuration," + baseDN);

            SearchResult results = connection.search(request);
            int count = results.getEntryCount();
            return count;
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("用户列表获取失败");
    }

    public int getUserOuCountByAdId(Integer adId) {

        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);

        LDAPConnection connection = null;
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection);
            //AD属性:获取组织ouDN;
            String baseDN = AdUtil.getBaseDN(adDef);
            String ouDN = AdUtil.getOuDN(adDef);
            SearchRequest request = new SearchRequest(ouDN,
                    SearchScope.SUB, "objectCategory=CN=Person,CN=Schema,CN=Configuration," + baseDN);

            SearchResult results = connection.search(request);
            int count = results.getEntryCount();
            return count;
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("用户列表获取失败");
    }

    public List<AdUser> getUsersByAdId(Integer adId) {

        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        LDAPConnection connection = null;

        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection);
            //AD属性:获取组织ouDN;
            String baseDN = AdUtil.getBaseDN(adDef);
            String ouDN = AdUtil.getOuDN(adDef);

            SearchRequest request = new SearchRequest(ouDN,
                    SearchScope.SUB, "objectCategory=CN=Person,CN=Schema,CN=Configuration," + baseDN);

            SearchResult results = connection.search(request);
            int count = results.getEntryCount();
            List<AdUser> adUsers = new ArrayList<>();

            for (SearchResultEntry entry : results.getSearchEntries()) {
                AdUser user = new AdUser();
                user.setUserName(entry.getAttribute("name").getValue());
                /*Attribute telephoneNumber = entry.getAttribute("telephoneNumber");
                if (telephoneNumber != null)
                    requestBean.setUserMobileNumber(telephoneNumber.getValue());
                Attribute mail = entry.getAttribute("mail");
                if (mail != null)
                    requestBean.setUserEmail(mail.getValue());*/
                adUsers.add(user);
            }
            return adUsers;

        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }

        throw Exceptions.newBusinessException("用户列表获取失败");
    }

    public List<AdComputer> getComputersByAdId(Integer adId) {
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        LDAPConnection connection = null;
        List<AdComputer> computers = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection);

            //AD属性:获取组织ouDN;
            String baseDN = AdUtil.getBaseDN(adDef);
            String ouDN = AdUtil.getOuDN(adDef);

            SearchRequest request = new SearchRequest(ouDN,
                    SearchScope.SUB, "objectCategory=CN=Computer,CN=Schema,CN=Configuration," + baseDN);
            SearchResult results = connection.search(request);
            int count = results.getEntryCount();
            for (SearchResultEntry entry : results.getSearchEntries()) {
                AdComputer computer= new AdComputer();
                computer.setComputerName(entry.getAttribute("name").getValue());
                computers.add(computer);
            }
            return computers;
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("计算机列表获取失败");
    }

    public boolean checkUser(String userName, Integer adId) {
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        LDAPConnection connection = null;
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection, "AD连接空指针异常");

            //AD属性:获取组织ouDN;
            String ouDN = AdUtil.getOuDN(adDef);

            String userDN = String.format("CN=%s,%s", userName, ouDN);
            SearchResultEntry entry = connection.getEntry(userDN);
            if (entry == null)
                return false;
            else {
                Attribute nameAttr = entry.getAttribute("samAccountName");
                if (nameAttr != null && nameAttr.getValue().equals(userName))
                    return true;
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }

        return false;
    }

    public void deleteUser(String userName, Integer adId) {
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        LDAPConnection connection = null;
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection, "AD连接空指针异常");

            //AD属性:获取组织ouDN;
            String ouDN = AdUtil.getOuDN(adDef);

            String userDN = String.format("CN=%s,%s", userName, ouDN);
            SearchResultEntry entry = connection.getEntry(userDN);
            Preconditions.checkNotNull(entry, "用户名不存在");
            LDAPResult result = connection.delete(userDN);
            if (result.getResultCode().getName().equals("success"))
                return ;
            else
                throw Exceptions.newBusinessException("用户删除失败");
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("用户删除失败");
    }

    public void deleteComputer(String computerName, Integer adId) {
        PkpmAdDef adDef = getAdDefByAdId(adId);
        Preconditions.checkNotNull(adDef);
        LDAPConnectionPool connectionPool = getConnectionPool(adDef);
        LDAPConnection connection = null;
        try {
            connection = connectionPool.getConnection();
            Preconditions.checkNotNull(connection, "AD连接空指针异常");

            //AD属性:获取组织ouDN;
            String ouDN = AdUtil.getOuDN(adDef);

            String computerDN = String.format("CN=%s,%s", computerName, ouDN);
            SearchResultEntry entry = connection.getEntry(computerDN);
            Preconditions.checkNotNull(entry, "计算机名不存在");
            LDAPResult result = connection.delete(computerDN);
            if (result.getResultCode().getName().equals("success"))
                return ;
            else
                throw Exceptions.newBusinessException("计算机删除失败");
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
            System.out.println("AD连接资源被释放");
        }
        throw Exceptions.newBusinessException("计算机删除失败");
    }


}
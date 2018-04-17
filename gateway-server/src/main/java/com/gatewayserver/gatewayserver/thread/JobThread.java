/**
 *
 */
package com.gatewayserver.gatewayserver.thread;

import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.impl.PkpmOperatorStatusDAOImpl;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.gatewayserver.gatewayserver.service.impl.DesktopServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangpengfei
 * @Description 查询并更新异步任务状态的线程类
 * @time 2018年3月29日 上午9:38:30
 */
public class JobThread implements Runnable {
    private static AtomicInteger count = new AtomicInteger(0);
    private String jobId;
    private String userName;
    private int adId;
    private long minutes = 5;
    @Resource
    private PkpmOperatorStatusDAO pkpmOperatorStatusDAO;
    @Resource
    private DesktopService workspaceService;

//	public PkpmOperatorStatusDAOImpl getPkpmOperatorStatusDAO() {
//		return pkpmOperatorStatusDAO;
//	}
//
//	@Resource
//	public void setPkpmOperatorStatusDAO(PkpmOperatorStatusDAOImpl pkpmOperatorStatusDAO) {
//		JobThread.pkpmOperatorStatusDAO = pkpmOperatorStatusDAO;
//	}
//
//	public WorkspaceServiceImpl getWorkspaceService() {
//		return workspaceService;
//	}
//
//	@Resource
//	public void setWorkspaceService(WorkspaceServiceImpl workspaceService) {
//		JobThread.workspaceService = workspaceService;
//	}

    //	public JobThread() {
    //无法获取bean
//			this.pkpmOperatorStatusDAO = (PkpmOperatorStatusDAOImpl) SpringBeanUtil.getBean("PkpmOperatorStatusDAO");
//			this.workspaceService = (WorkspaceServiceImpl) SpringBeanUtil.getBean("WorkspaceServiceImpl");
//	}
    public JobThread(PkpmOperatorStatusDAO pkpmOperatorStatusDAO, DesktopService workspaceService) {
        this.pkpmOperatorStatusDAO = pkpmOperatorStatusDAO;
        this.workspaceService = workspaceService;
    }

//	public JobThread(String jobId, String userName, Integer adId) {
//		super();
//		this.jobId = jobId;
//		this.userName = userName;
//		this.adId = adId;
//	}

    public static void main(String[] args) {
        PkpmOperatorStatusDAOImpl pkpmOperatorStatusDAO = new PkpmOperatorStatusDAOImpl();
        DesktopServiceImpl workspaceService = new DesktopServiceImpl();
        JobThread jobThread = new JobThread(pkpmOperatorStatusDAO, workspaceService);
        Thread t1 = new Thread(jobThread, "线程一");
        // Thread t2 = new Thread(new JobThread(),"线程二");
        // Thread t3 = new Thread(jobThread,"线程三");
        t1.start();
        // t2.start();
        // t3.start();
    }

    @Override
    public void run() {
        System.out.println("JobThread.run()");
        try {
            if (pkpmOperatorStatusDAO == null || workspaceService == null) {
                return;
            }
            // 查询未完成的开户任务
            List<PkpmOperatorStatus> list = pkpmOperatorStatusDAO.selectNotFinished();
            for (int i = 0; i < list.size(); i++) {
                jobId = list.get(i).getJobId();
                userName = list.get(i).getUserName();
                adId = list.get(i).getAdId();
                // 根据任务id更新操作状态表
                workspaceService.updateOperatorStatus(jobId, userName, adId, minutes);
            }
            count.addAndGet(1);
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "count:" + count);
            Thread.sleep(100);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
    }

}

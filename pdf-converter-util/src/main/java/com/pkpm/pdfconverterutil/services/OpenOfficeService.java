package com.pkpm.pdfconverterutil.services;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.pkpm.httpclientutil.common.util.PropertiesUtil;


public class OpenOfficeService {

	/**
	 * linux系统下libreoffice安装目录
	 */
	private static final String OFFICE_HOME_LINUX = PropertiesUtil.getProperty("converter.properties", "office.home.linux");
	
	/**
	 * windows系统下libreoffice安装目录
	 */
	private static final String OFFICE_HOME_WINDOWS = PropertiesUtil.getProperty("converter.properties", "office.home.windows");
	
    private static OfficeManager officeManager = null; //Couldn't make NullObject b/c of poor OfficeManager declaration

    public static void initialize(List<Long> listenAtPorts) {
        if(null == officeManager) {
            OpenOfficeConfig.setPorts(listenAtPorts);
            int[] ports = OpenOfficeConfig.getPorts();

            Logger.getLogger(OpenOfficeService.class.getName()).log(Level.INFO,
                    "Initializing OfficeManager in ports: "+Arrays.toString(ports));

            //OS X: cd /Applications/LibreOffice.app/Contents/MacOS/
            //      sudo ln -s ./soffice soffice.bin
            // FIXED in master:
            //   https://github.com/nuxeo/jodconverter/commit/30eb0644a217081ce936a866ac6be411c62c6f49
            officeManager = new DefaultOfficeManagerConfiguration()
                    .setPortNumbers(ports)
                    .setOfficeHome(new File(getLibreOfficeHome()))
                    
                    //设置任务执行超时为10分钟  
                    .setTaskExecutionTimeout(1000 * 60 * 10L) 
                    //设置任务队列超时为24小时  
                 	.setTaskQueueTimeout(1000 * 60 * 60 * 24L)
                    .buildOfficeManager();
        } else {
            Logger.getLogger(OpenOfficeService.class.getName()).log(Level.WARNING,
                    "OfficeManager is already initialized!");
        }
    }
    
    /** 
     * 获取libreOffice安装目录
     * @return 
     */  
    public static String getLibreOfficeHome() {
        String osName = System.getProperty("os.name");
  
        if (Pattern.matches("Linux.*", osName)) {
            //获取linux系统下libreoffice主程序的位置 
            return OFFICE_HOME_LINUX; 
        } else if (Pattern.matches("Windows.*", osName)) { 
            //获取windows系统下libreoffice主程序的位置 
            return OFFICE_HOME_WINDOWS;
        }
        
        return null;  
    } 

    public static void start() {
        long startTime = System.currentTimeMillis();
        officeManager.start();
        logElapsedTime(startTime, "Started");
    }

    public static void stop() {
        long startTime = System.currentTimeMillis();
        officeManager.stop();
        logElapsedTime(startTime, "Stopped");
    }

    public static OfficeManager getOfficeManager(){
        return officeManager;
    }

    public static void logElapsedTime(long startTime, String prefix) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        Logger.getLogger(OpenOfficeService.class.getName()).log(Level.INFO,
                String.format(prefix + " in %dms", elapsedTime));
    }
}

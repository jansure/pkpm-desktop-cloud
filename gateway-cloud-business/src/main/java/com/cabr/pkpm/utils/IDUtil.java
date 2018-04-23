package com.cabr.pkpm.utils;

import java.util.Random;
import java.util.UUID;

public class IDUtil {

	/**
	 * 订单id生成
	 */
	public static long genOrderId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(100);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(IDUtil.genOrderId());
		System.out.println(generateGUID());
	}
	
	 public static final String generateGUID()  
	    {   
	        UUID uuid=UUID.randomUUID();  
	          return uuid.toString();  
	    }  
	
}

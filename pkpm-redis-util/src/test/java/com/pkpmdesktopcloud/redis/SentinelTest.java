
/**    
 * @Title: SentinelTest.java  
 * @Package com.pkpmdesktopcloud.redis  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年5月25日  
 * @version V1.0    
 */

package com.pkpmdesktopcloud.redis;

/**
 * @ClassName: SentinelTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wangxiulong
 * @date 2018年5月25日
 * 
 */
public class SentinelTest {

	public static void main(String[] args) {
		RedisCache cache = new RedisCache("test123");
		for (int i = 0; i < 5; i++) {
			
			cache.putObject(i, i);
		}
		System.out.println(cache.getObject(0));
		System.out.println(cache.getObject(4));
//		cache.clear();
		cache.destroy();
		System.out.println("OK");
	}

}

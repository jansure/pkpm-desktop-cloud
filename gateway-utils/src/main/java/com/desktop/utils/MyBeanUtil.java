  
/**    
 * @Title: BeanUtil.java  
 * @Package com.desktop.utils  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年4月17日  
 * @version V1.0    
 */  
    
package com.desktop.utils;

import java.lang.reflect.Field;

/**  
 * @ClassName: BeanUtil  
 * @Description: 对象操作类
 * @author wangxiulong  
 * @date 2018年4月17日  
 *    
 */
public class MyBeanUtil<T> {
	
	/**
	 * 
	 * @Title: setPropertyNull  
	 * @Description: 对象所有属性置空
	 * @param t 对象
	 * @throws
	 */
	public T setPropertyNull(T t) {
		Class<? extends Object> clazz = t.getClass();
		//创建实例化
        Object object = null;
		try {
			object = clazz.newInstance();
			
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields) {
				field.setAccessible(true);
				field.set(object, null);
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        
		
		return (T)object;
	}

}

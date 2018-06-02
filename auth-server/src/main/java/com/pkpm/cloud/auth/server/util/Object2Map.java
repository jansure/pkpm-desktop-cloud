package com.pkpm.cloud.auth.server.util;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.util.HashMap;  
import java.util.Map; 


public class Object2Map {
	 public static Map<String, String> getFieldVlaue(Object obj) throws Exception {  
	        Map<String, String> mapValue = new HashMap<String, String>();  
	        Class<?> cls = obj.getClass();  
	        Field[] fields = cls.getDeclaredFields();  
	        for (Field field : fields) {  
	            String name = field.getName();  
	            String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());  
	            Method methodGet = cls.getDeclaredMethod(strGet);  
	            Object object = methodGet.invoke(obj);  
	            String value = object != null ? object.toString() : "";  
	            mapValue.put(name, value);  
	        }  
	        return mapValue;  
	  } 
	 public static Map<String,Object> Obj2Map(Object obj) throws Exception{
         Map<String,Object> map=new HashMap<String, Object>();
         Field[] fields = obj.getClass().getDeclaredFields();
         for(Field field:fields){
             field.setAccessible(true);
             map.put(field.getName(), field.get(obj));
         }
         return map;
     }
}

package com.cabr.pkpm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author yangpengfei
 *
 */
public class StringUtil {
	/**
	 * 将 String 类型转化为 int 类型
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str) {
		try {
		    int b = Integer.parseInt(str);
		    return b;
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    return -1;
		}
	}
	
	/**
	 * 将 String 类型转化为 long 类型
	 * @param str
	 * @return
	 */
	public static long stringToLong(String str) {
		try {
		    long b = Long.parseLong(str);
		    return b;
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    return -1;
		}
	}
	/**
	 * 8-64个字符
	 * 不能与用户名或倒序的用户名相同
	 * 以字母开头
	 * 至少包含以下字符的4种：大写字母、小写字母、数字、和特殊字符
	 * @param
	 * @return
	 */
	public static boolean checkPassword(String username,String password){
		 boolean flag1 = password.matches(".*?[^a-zA-Z\\d]+.*?")  
	                  && password.matches(".*?[a-z]+.*?")  
	                  && password.matches(".*?[A-Z]+.*?")  
	                  && password.matches(".*?[\\d]+.*?")
	                  && password.subSequence(0, 1).toString().matches("^[A-Za-z]");
		 
		  boolean flag2 = false;
		  if(password.length() >=8 && password.length() <= 63){
		    	 flag2 = true;
		  } 
		  boolean flag3 = checkPasswordIsUsernameOrReverseUsername(username,password);
		  boolean flag = flag1 && flag2 && flag3;
		  
		  return flag;
	}
	
	public static boolean checkPasswordIsUsernameOrReverseUsername(String username,String password){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(username);
		stringBuilder.reverse();
		String reverseUsername= stringBuilder.toString();
		boolean flag3 = true;
		if(password.equals(username) ||  password.equals(reverseUsername)){
			flag3 = false;
			return flag3;
		}
		return flag3;
	}
	
	/**
	 * 提取字符串中的整数
	 * @param str
	 * @return
	 */
	public static String substr(String str) {
        // 需要取整数和小数的字符串
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例. 
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }

	
}

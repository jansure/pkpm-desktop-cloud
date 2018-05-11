package com.desktop.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String double2percentage(Double d) {
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumIntegerDigits(3);
        num.setMaximumFractionDigits(2);
        String format = num.format(d);
        return format;
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
	
	/**
	 * 
	 * @Title: getIntegerByStr  
	 * @Description: 获取字符串中的数字，如group1->1
	 * @param strInput 输入字符串
	 * @return List<Integer>    返回数字集合
	 */
	public static List<Integer> getIntegerByStr(String strInput){
		
		List<Integer> resultList = new ArrayList<Integer>();
		
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(strInput);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        //遍历数组转换数据类型
        for(String s:strArr){
        	if(StringUtils.isEmpty(s)) {
        		continue;
        	}
        	
            resultList.add(new Integer(s));
        }
		
		return resultList;
	}
	
	public static void main(String[] args) {
		
		String str="love23next234csdn3423javaeye";
		System.out.println(getIntegerByStr(str));
	}
}

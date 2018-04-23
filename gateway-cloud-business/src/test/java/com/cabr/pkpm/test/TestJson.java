package com.cabr.pkpm.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestJson {
	public static void main(String[] args) {
		/*String s="{'a':'aa','c':'c'}";
		Map<String,String> map  = (Map<String, String>) JSON.parse(s);
		System.out.println(map.get("a")+"==="+map.get("c"));*/
		
		/*for (Entry<String, String> en : map.entrySet()) {
			System.out.println(en.getKey()+"---------"+en.getValue());
		}*/
		/*String s="[{'a':'aa','c':'cc'},{'a':'cc','c':'cccccc'}]";
		//Map<String,String> map  = (Map<String, String>) JSON.parse(s);
		JSONArray parseArray = (List)JSON.parseArray(s);
		List<Map<String,String>> list = (List)parseArray;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
		  System.out.println(map.get("a")+"======="+map.get("c"));
		}
		for (Entry<String, String> en : map.entrySet()) {
			System.out.println(en.getKey()+"---------"+en.getValue());
		}*/
	}
	
	public static LocalDate stringToDate(String strTime, String partern) {
		 if(strTime.isEmpty() || partern.isEmpty()) return null;
		        DateTimeFormatter df = DateTimeFormatter.ofPattern(partern.trim());
		        LocalDateTime datetime = LocalDateTime.parse(strTime,df);
		        return datetime.toLocalDate();
		}  
}

package com.cabr.pkpm.test;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestPost {
	/*
	 * POST /v2/{project_id}/notifications/sms
{
"endpoint": "+86159****1990",
"message": "Sms message test",
"sign_id":"94d3b63a5dfb475994d3ac34664e2346"
}
	 */
	public static void main(String[] args) {
		getToken();
//		Map<String,String> map =new HashMap<>();
//		map.put("endpoint","+8615517156082");
//		map.put("message","hahasdsf");
//		map.put("sign_id","af6cb8f5a6954290b6f5f0819f12018f");
//		String jsonStr = JSON.toJSONString(map);
//		System.out.println(jsonStr);
//		doPost("https://smn.cn-north-1.myhwclouds.com/v2/d3e6d7eda4094efc92d31aa010945980/notifications/sms", jsonStr);
	}
	
	public static String getToken(){
		Map<String,Object> project = new HashMap<String, Object>();
		project.put("name", "cn-north-1");
		Map<String,Object> scope = new HashMap<String, Object>();
		scope.put("project", project);
		
		Map<String,Object> domain = new HashMap<String, Object>();
		domain.put("name", "liboxi");
		Map<String,Object> user = new HashMap<String, Object>();
		user.put("name", "liboxi");
		user.put("password", "caBr905");
		user.put("domain", domain);
		Map<String,Object> password = new HashMap<String, Object>();
		password.put("user", user);
		ArrayList<String> methods = new ArrayList<String>();
		methods.add("password");
		
		Map<String,Object> identity = new HashMap<String, Object>();
		identity.put("methods", methods);
		identity.put("password", password);
		Map<String,Object> auth = new HashMap<String, Object>();
		auth.put("identity", identity);
		auth.put("scope", scope);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("auth", auth);
		Object json = JSON.toJSON(param);
		System.out.println(json);
		String result = doPost("https://iam.cn-north-1.myhwclouds.com/v3/auth/tokens", json.toString());
		return result;
	}
	/**
	 * @param postUrl
	 * @param parameters
	 * @return
	 */
	public static String doPost(String postUrl, String parameters) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = null;

		HttpPost httpPost = new HttpPost(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpPost.setConfig(requestConfig);
		try {
			//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterList, "UTF-8");
			StringEntity entity = new StringEntity(parameters, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);
			//CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost);
			/*Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				if(header.getName().equals("X-Subject-Token")){
					System.out.println(header.getValue());
					return header.getValue();
				}
			}*/
			
			HttpEntity httpEntity = response.getEntity();
			retStr = EntityUtils.toString(httpEntity, "UTF-8");
			// 释放资源
//			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * 处理登录后的get请求
	 * 
	 * @param args
	 */
	public  String doGet(String getUrl, List<NameValuePair> parameterList) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = null;
//		if (cookieStore != null) {
//			closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
//		} else {
//			closeableHttpClient = httpClientBuilder.build();
//		}
		try {

			String str = EntityUtils.toString(new UrlEncodedFormEntity(parameterList, Consts.UTF_8));
			// HttpGet request = new HttpGet(getUrl+"?"+str);//这里发送get请求
			HttpGet request = new HttpGet(getUrl + str);// 这里发送get请求

			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000)
					.build();
			request.setConfig(requestConfig);

			CloseableHttpResponse response = closeableHttpClient.execute(request);
			// setCookieStore(response);

			HttpEntity httpEntity = response.getEntity();
			retStr = EntityUtils.toString(httpEntity, "UTF-8");
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	
	

}

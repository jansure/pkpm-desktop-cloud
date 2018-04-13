package com.cabr.pkpm.utils.httpclient;



import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 采用 org.apache.httpcomponents 包下的 httpClient 封装的http工具类
 * @author jingqy
 * @version 0.1
 */
public class HttpClientUtils {

    private static RequestConfig requestConfig = RequestConfig.custom()
        .setSocketTimeout(1000)
        .setConnectTimeout(1000)
        .setConnectionRequestTimeout(1000)
        .build();
    private static HttpClientUtils instance = null;
    private HttpClientUtils(){}

    /**
     * 单例模式获取1个HttpClientUtils实例
     * @return HttpUtil
     */
    public static HttpClientUtils getInstance(){
        if (instance == null) {
            instance = new HttpClientUtils();
        }
        return instance;
    }

    /**
     * HttpPost请求
     * @param url url
     * @param params 参数
     * @param headers 头信息
     * @param encode 编码
     * @return HttpResponse
     */
   
   public static HttpResponse httpPostJson(String url,String strJson, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        
        StringEntity stringEntity = new StringEntity(strJson, Charset.forName("UTF-8"));
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
        	httpPost.setEntity(stringEntity);
            httpResponse = closeableHttpClient.execute(httpPost);
            httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



    /**
     * httpGet 请求
     * @param url url地址
     * @param params 参数map
     * @param headers 头信息
     * @param encode 编码信息
     * @return HttpResponse
     */
    public static HttpResponse httpGetForm(String url,Map<String,String> headers,String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(),entry.getValue());
            }
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if ( httpResponse != null )
                    httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    
    /**
     * get 请求,传递参数
     * @param getUrl
     * @param parameterList
     * @return
     */
    public  static String doGet(String getUrl, List<NameValuePair> parameterList) {
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


    


    /**
     * http-delete 请求
     * @param url 请求地址
     * @param headers 请求头信息
     * @param encode 编码
     * @return HttpResponse
     */
    public static HttpResponse httpDelete(String url,  Map<String,String> headers, String encode) {
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient  = HttpClientBuilder.create().build();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        if ( headers != null && headers.size() > 0 ) {
            for ( String key :headers.keySet() ){
                httpDelete.setHeader( key, headers.get(key) );
            }
        }

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpDelete);
            HttpEntity entity = closeableHttpResponse.getEntity();
            String content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse!=null )
                    closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * HttpPut
     * @param url url
     * @param params 参数map
     * @param headers 头信息
     * @param encode 解码字符集
     * @return HttpResponse
     */
    @SuppressWarnings("deprecation")
	public static HttpResponse httpPutJson(String url, String strJson, Map<String,String> headers, String encode){
        HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(),entry.getValue());
            }
        }

        StringEntity stringEntity = new StringEntity(strJson, Charset.forName("UTF-8"));
        httpPut.setEntity(stringEntity);
        
        String content = null;
        try {
        	HttpClient httpClient = new DefaultHttpClient();
        	org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



}

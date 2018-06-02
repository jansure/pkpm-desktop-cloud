package com.pkpm.httpclientutil.test;

import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.builder.HCB;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpHeader;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.SSLs.SSLProtocolVersion;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

public class DemoYpf {

    public static void main(String[] args) {
        // POST获取token
        String url = "https://iam.cn-north-1.myhuaweicloud.com/v3/auth/tokens";
        String strJson = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"name\":\"liboxi\",\"password\":\"caBr905\",\"domain\":{\"name\":\"liboxi\"}}}},\"scope\":{\"project\":{\"id\":\"d3e6d7eda4094efc92d31aa010945980\"}}}}";
        try {
            Header[] headers = HttpHeader.custom()
                    .contentType("application/json")
                    .build();
            HCB hcb = HCB.custom()
                    .timeout(10000)        //超时，设置为1000时会报错
//						.pool(100, 10)    	//启用连接池，每个路由最大创建10个链接，总连接数限制为100个，不能用连接池，否则会报错
                    .sslpv(SSLProtocolVersion.TLSv1_2)    //可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
                    .ssl()                    //https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                    .retry(5)                    //重试5次
                    ;

            HttpClient client = hcb.build();
            //插件式配置请求参数（网址、请求参数、编码、client）
            HttpConfig config = HttpConfig.custom()
                    .headers(headers, true)    //设置headers，不需要时则无需设置
                    .client(client)
                    .url(url)            //设置请求的url
                    .json(strJson)        //设置请求参数，没有则无需设置
                    .encoding("utf-8")  //设置请求和返回编码，默认就是Charset.defaultCharset()
                    ;

            String result = HttpClientUtil.post(config);
            String token = "";

            Header[] respHeaders = HttpClientUtil.respHeaders(config.method(HttpMethods.POST));

            for (Header header : respHeaders
                    ) {
                if (header.getName().equals("X-Subject-Token")) {
                    token = header.getValue();
                }
            }

            System.out.println(token);

//	        //获取状态码
//	      	int status = HttpClientUtil.status(config.method(HttpMethods.POST));
//	      	System.out.println(status);

        } catch (HttpProcessException e) {
            System.out.println(e.getMessage());
        } catch (Exception ee) {
            System.out.println(ee.getMessage());
        }


        // GET查询云桌面服务详情
//		String url = "https://workspace.cn-north-1.myhuaweicloud.com/v1.0/d3e6d7eda4094efc92d31aa010945980/workspaces";
//        String token = "MIIRswYJKoZIhvcNAQcCoIIRpDCCEaACAQExDTALBglghkgBZQMEAgEwghABBgkqhkiG9w0BBwGggg-yBIIP7nsidG9rZW4iOnsiZXhwaXJlc19hdCI6IjIwMTgtMDMtMTRUMDA6MzI6NDcuODg4MDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwiY2F0YWxvZyI6W10sInJvbGVzIjpbeyJuYW1lIjoidGVfYWRtaW4iLCJpZCI6ImUyZDc1NDI5NTQ5MDQ0ZDJiMzEzZmJkZDExNTIwZjdkIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV9rdm1tNjAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfZGVmb2ciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX3ZhdF9pbnZvaWNlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX20zIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX3N1cGVyX3Jlc29sdXRpb24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX2hhbmR3cml0aW5nIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX3BhdGhfcHJvZ3JhbSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nlc19hZ3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF92aXBfYmFuZHdpZHRoIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV92MTAwIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX29jcl9iYW5rX2NhcmQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kbHMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kd3NfcG9jIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX29jcl9iYXJjb2RlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZGRzX3JlcGxpY2EiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tbHNfc3RkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaW90LXRyaWFsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX2Rpc3RvcnRpb25fY29ycmVjdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3VxdWVyeSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nsb3VkY2MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NxdWlja2RlcGxveSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Jkc19od3NxbCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nsb3VkdGFibGUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZGVoIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX2ltYWdlX2NsYXJpdHlfZGV0ZWN0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2NjX2Ric3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jcnMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9sbGQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZnBnYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19vY3JfZ2VuZXJhbF90YWJsZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX3BjX3ZlbmRvcl9zdWJ1c2VyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfSU0iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfYmluX3BhY2tpbmciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfcmVjeWNsZWJpbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX0xUUyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19ncHVfY2EiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zYXBoYW5hIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZ2F0ZWRfZWNzX3JlY3ljbGViaW4iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jbG91ZGlkZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19ub3JtYWxleGNsdXNpdmVfYzMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX3ZlaGljbGVfbGljZW5zZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19kaXNwYXRjaF9wbGFubmluZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2hzcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc190aW1lX2Fub21hbHkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9iY3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9mZ3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9vbXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9nZXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfaGlnaGJhbmQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZXQyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZGVzcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19IaWdoUGVyZm9ybWFuY2VIMUhBTkEiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9sZWdhY3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfcGlja3VwX3BsYW5uaW5nIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9DTi1TT1VUSC0zIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX29jcl9pZF9jYXJkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX25vcm1hbF9zMyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX21vYmlsZXRlc3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jZG0iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jcHRzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV9wNCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RucyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19pbWFnZV9yZWNhcHR1cmVfZGV0ZWN0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2NjX3NjcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Fpc19vY3JfZHJpdmVyX2xpY2Vuc2UiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfb2NyX3FyX2NvZGUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kY3NfaW1kZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3RtcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3NjY19zc2EiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfZGFya19lbmhhbmNlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2gxIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfSGlnaElPSTMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kcnMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfaDMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF92b2ljZWNhbGwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfaW1hZ2VfYW50aXBvcm4iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfdHRzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbmF0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWxhc3RpY3NlYXJjaCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lvdDAxIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2Rpc2tpbnRlbnNpdmUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9yZHNfY3VzdG9tZXJjbG91ZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FwaWciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kY3NfbWVtY2FjaGVkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYWlzX2Fzcl9zZW50ZW5jZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NjcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NjZV93aW4iLCJpZCI6IjAifV0sInByb2plY3QiOnsiZG9tYWluIjp7Im5hbWUiOiJsaWJveGkiLCJpZCI6ImJlMDQ5NGE0MDc0MDRjNjRhZDNmMWI2YjIyNTRiNzBhIn0sIm5hbWUiOiJjbi1ub3J0aC0xIiwiaWQiOiJkM2U2ZDdlZGE0MDk0ZWZjOTJkMzFhYTAxMDk0NTk4MCJ9LCJpc3N1ZWRfYXQiOiIyMDE4LTAzLTEzVDAwOjMyOjQ3Ljg4ODAwMFoiLCJ1c2VyIjp7ImRvbWFpbiI6eyJuYW1lIjoibGlib3hpIiwiaWQiOiJiZTA0OTRhNDA3NDA0YzY0YWQzZjFiNmIyMjU0YjcwYSJ9LCJuYW1lIjoibGlib3hpIiwiaWQiOiI3NDIwYzM4MDNkMWE0MDlmYWZlN2NmZTI0OWJhNTgwZSJ9fX0xggGFMIIBgQIBATBcMFcxCzAJBgNVBAYTAlVTMQ4wDAYDVQQIDAVVbnNldDEOMAwGA1UEBwwFVW5zZXQxDjAMBgNVBAoMBVVuc2V0MRgwFgYDVQQDDA93d3cuZXhhbXBsZS5jb20CAQEwCwYJYIZIAWUDBAIBMA0GCSqGSIb3DQEBAQUABIIBABjd6LLMf33CfTssKPlE+SUtwGKi3TNv+7WfHenXZPuN25wC04auIwU-jRBmSS9d59DUXjiaWNsdSB20c+LU+Zgo31cOd0A4rAbSIjzn9FiJId2EQlA5XzFQw45dWpknMGM8uNgp-7ZEzFWJlsVjiNwUje4h+M5O6ue9kJRIvpvSdwaofJpYl2SGW9Kr68MJ3SoTp9PuOjMj566W1Jc6uVQTX2jFnE7xtXpk5C5TIhg5nHh7weS8rymxp7D2CCO+CDM-5lb8BToHHQCNxoTgOCNkdhB9DvPfyGma816aubcvu81NNjeFZ4wgqdf0o03Yf7TXgqnUFpQNdqD6dOl8-2g=";
//        try {
//	        Header[] headers = HttpHeader.custom()
//	        								.contentType("application/json")
//	        								.other("X-Auth-Token", token)
//	        								.build();
//	        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
//	  		HCB hcb 				= HCB.custom()
//	  											.timeout(10000) 		//超时
////	  											.pool(100, 10)    	//启用连接池，每个路由最大创建10个链接，总连接数限制为100个
//	  											.sslpv(SSLProtocolVersion.TLSv1_2) 	//可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
//	  											.ssl()  			   		//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
//	  											.retry(3)					//重试5次
//	  											;
//	  		
//	  		HttpClient client = hcb.build();
//	  		//插件式配置请求参数（网址、请求参数、编码、client）
//	      	HttpConfig config = HttpConfig.custom()
//	      									.client(client)
//	      									.headers(headers)	//设置headers，不需要时则无需设置
//	      									.url(url)			//设置请求的url
//	      									.encoding("utf-8")  //设置请求和返回编码，默认就是Charset.defaultCharset()
//	      									;
//	    	        
//	        String result = HttpClientUtil.get(config);
//	        System.out.println(result);
//        }
//        catch (HttpProcessException e) {
//			System.out.println(e.getMessage());
//		}
//		catch (Exception ee)
//		{
//			System.out.println(ee.getMessage());
//		}

        // http请求   云桌面开户成功通知短信
//		String url = "http://49.4.8.123:8083/product/sendClientMessage";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userMobileNumber", "18410429882");
//		map.put("workId", "151834681009012");
//		Header[] headers = HttpHeader.custom()
//									.contentType("application/x-www-form-urlencoded")
//									.build();
//		HttpConfig config = HttpConfig.custom()
//									.headers(headers,true)	//设置headers，不需要时则无需设置
//									.url(url)			//设置请求的url
//									.map(map)
//									.encoding("utf-8")  //设置请求和返回编码，默认就是Charset.defaultCharset()
//									;
//		try {
//			String result = HttpClientUtil.post(config);
//			System.out.println(result);
//		}
//		catch (HttpProcessException e) {
//			System.out.println(e.getMessage());
//		}
//		catch (Exception ee)
//		{
//			System.out.println(ee.getMessage());
//		}


    }

}

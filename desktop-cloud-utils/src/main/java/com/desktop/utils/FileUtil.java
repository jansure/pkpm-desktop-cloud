package com.desktop.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件操作工具
 * 
 * @author yangpengfei
 * @date 2017/12/25
 *
 */
public class FileUtil {
	/**
	 * 文件在线打开或指定路径下载
	 * 
	 * @param filePath
	 * @param isOnLine
	 * @param response
	 * @throws IOException
	 */
	public static final void downloadFile(String filePath,String originFileName, boolean isOnLine, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		InputStream inputStream = null;
		try {
			URL httpurl = new URL(filePath);
			if (httpurl != null) {
				// 打开网络连接
				HttpURLConnection httpURLConnection = (HttpURLConnection) httpurl.openConnection();
				// 设置GET方式连接
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setConnectTimeout(3000);
				// 需要输入
				httpURLConnection.setDoInput(true);
				// 需要输出
				httpURLConnection.setDoOutput(true);
				// 不允许缓存
				httpURLConnection.setUseCaches(false);
				// 维持长连接
				httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
				// 获取连接返回的标识码
				int responseCode = httpURLConnection.getResponseCode();
				// 200表示连接成功
				if (responseCode == 200) {
					// 获取网络输入流
					inputStream = httpURLConnection.getInputStream();
					if (inputStream == null) {
						String msg = ("Can not find a java.io.InputStream.");
						throw new IllegalArgumentException(msg);
					}
					// 文件名应该编码成UTF-8
					String fileName = getFileNameFromUrl(filePath);
					String userAgent = request.getHeader("User-Agent");
					// 针对IE或者以IE为内核的浏览器：  
		            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {  
		            	fileName = URLEncoder.encode(fileName, "UTF-8");

		            	if(StringUtils.isNotEmpty(originFileName)){
							originFileName = URLEncoder.encode(originFileName, "UTF-8");
						}

		            } else {
		                // 非IE浏览器的处理：  
		            	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

		            	if(StringUtils.isNotEmpty(originFileName)){
							originFileName = new String(originFileName.getBytes("UTF-8"), "ISO-8859-1");
						}


					}
					BufferedInputStream bins = new BufferedInputStream(inputStream);
					// 输出文件用的字节数组，每次发送1024个字节到输出流
					byte[] buf = new byte[1024];
					int len = 0;

					// 非常重要
					response.reset();
					// 允许跨域调用
					response.setHeader("Access-Control-Allow-Origin", "*");

					if(StringUtils.isBlank(originFileName) && StringUtils.isNotBlank(fileName))
						originFileName = fileName;

					// 在线打开方式
					if (isOnLine) {
						URL u = new URL(filePath);
						response.setContentType(u.openConnection().getContentType());
						response.setHeader("Content-Disposition", "inline; filename=" + originFileName);
					} else {
						// 纯下载方式
						response.setContentType("application/x-msdownload");
						// 客户使用目标另存为对话框保存指定文件
						response.setHeader("Content-Type","text/plain");
						//response.addHeader("Content-Disposition","attachment;filename=" + new String(originFileName.getBytes("UTF-8"), "ISO-8859-1"));
						response.addHeader("Content-Disposition","attachment;filename=" + originFileName);

					}
					OutputStream outs = response.getOutputStream();
					// 保存文件
					while ((len = bins.read(buf)) > 0) {
						outs.write(buf, 0, len);
					}
					bins.close();
					outs.close();
					httpURLConnection.disconnect();
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("抛出异常！！");
			throw new IOException(e);
		}
	}

	/**
	 * 从URL获取JSON字符串
	 * 
	 * @param url
	 * @return
	 */
	public static final String loadJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 从http url下载文件的方法
	 * 
	 * @param url
	 *            文件所在的http路径
	 * @param dir
	 *            文件存放目标路径
	 * @return
	 */
	public static final Boolean downloadFromUrl(String url, String dir) {
		try {
			URL httpurl = new URL(url);
			String fileName = getFileNameFromUrl(url);
			File f = new File(dir + fileName);
			FileUtils.copyURLToFile(httpurl, f);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从url获取文件名
	 * 
	 * @param url
	 *            文件所在的http路径
	 * @return
	 */
	public static final String getFileNameFromUrl(String url) {
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if (index > 0) {
			name = url.substring(index + 1);
			if (name.trim().length() > 0) {
				return name;
			}
		}
		return name;
	}

	/**
	 * http路径下原始txt文档需要使用utf-8编码保存，通过Http地址返回JSON数据，进行解析
	 * 
	 * @param configUrl
	 *            http地址
	 * @return
	 */
	public static final String getHttpResponse(String configUrl) {
		BufferedReader in = null;
		StringBuffer result = null;
		try {
			URI uri = new URI(configUrl);
			URL url = uri.toURL();
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			connection.connect();
			result = new StringBuffer();
			// 读取URL的响应，构造一个BufferedReader类来读取文件
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			// 使用readLine方法，一次读一行
			while ((line = in.readLine()) != null) {
				result.append(System.lineSeparator() + line);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 若响应不为null则需要关闭流
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: getFileType  
	 * @Description: 获取文件类型
	 * @param fileName 文件名
	 * @return String  文件类型(.mp4)
	 * @throws
	 */
	public static String getFileType(String fileName){
		String type = "";
		if(StringUtils.isEmpty(fileName)) {
			return type;
		}
		
		int index = fileName.lastIndexOf(".");
		if(index > -1) {
			type = fileName.substring(index, fileName.length());
		}
		return type;
	}

	public static void main(String[] args) {
		// // 测试从http url下载文件的方法
		// String url = "http://49.4.66.187:8888/files/法律条款说明test1.txt";
		// String dir = "F:/test/";
		// System.out.println("下载结果:" + FileUtil.downloadFromUrl(url, dir));

		// 测试通过Http地址返回JSON数据，进行解析
		try {
			// 原始txt文档需要使用utf-8编码保存
			String url = "http://49.4.8.123:8888/files/BIM协同设计管理云平台.pdf";
			// String json = FileUtil.getHttpResponse(url);
			// System.out.println(json);
			System.out.println(FileUtil.getFileNameFromUrl(url));
			// 测试从URL获取json
			String json = FileUtil.loadJson("http://49.4.8.123:8888/files");
			System.out.println(json);
			
			String fileName = "aa.bb.cc.mp4";
			System.out.println(getFileType(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
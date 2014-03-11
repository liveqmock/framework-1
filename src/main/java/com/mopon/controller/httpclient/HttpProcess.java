package com.mopon.controller.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Component;


/**
 * <p>Description:HTTP CLIENT类用于发送HTTP请求</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("httpProcess")
public class HttpProcess {

	/**
	 * GBK编码
	 */
	//private final String CONTENT_CHARSET_GBK = "GBK";
	
	/**
	 * UTF-8编码
	 */
	private final String CONTENT_CHARSET_UTF8 = "UTF-8";
	

	/** 
	 * 方法用途: 发送GET请求
	 * 实现步骤: 用于发送GET请求，分为RESTUF和KV方式<br>
	 * @param:String url发送的请求地址 Map params 参数MAP boolean type 发送类型
	 * @return byte[] 得到响应的byte数据数组
	 */
	public byte[] doGet(String url, Map<String, String> params,
			boolean type) {
		HttpClient httpClient = new HttpClient();
		//httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET_GBK);
		List<Header> headers = new ArrayList<Header>();  
        headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));  
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);  
		StringBuffer queryString = new StringBuffer("");
		if (type) {
			url += "/";
		} else {
			url += "?";
		}
			if(params != null) {
			try {
				for (String key : params.keySet()) {
					String param = "";
					if (type) {
						param = URLEncoder.encode(params.get(key), CONTENT_CHARSET_UTF8) + "/";
					} else {
						param = key + "=" + URLEncoder.encode(params.get(key), CONTENT_CHARSET_UTF8) + "&";
					}
					queryString.append(param);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			queryString.delete(queryString.length() - 1, queryString.length());
		}
		System.out.println(url + queryString.toString());
		GetMethod getMethod = new GetMethod(url + queryString.toString());
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err
						.println("Method failed:" + getMethod.getStatusLine());
			}
			// 读取内容
			return getMethod.getResponseBody();
			// 处理内容
			// System.out.println(new String(responseBody));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	/** 
	 * 方法用途: 发送POST请求
	 * 实现步骤: 用于发送POST请求<br>
	 * @param:String url发送的请求地址 Map params 参数MAP 
	 * @return byte[] 得到响应的byte数据数组
	 */
	public byte[] doPost(String url, Map<String, String> params) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
		NameValuePair[] nameValuePairs = new NameValuePair[params.size()];
		if(params != null) {			
			//try {
				int index = 0;
				for (String key : params.keySet()) {
					NameValuePair nameValuePair = new NameValuePair(key,
							params.get(key));
					nameValuePairs[index] = nameValuePair;
					index++;
				}
			/*} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
		}
		try {
			
			postMethod.setRequestBody(nameValuePairs);
			
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:"
						+ postMethod.getStatusLine());
			}
			return postMethod.getResponseBody();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		String mobiles = "13590325680,13510507931,13266767891,15867161782,13713668708,18318036418,18664568026,15989435236,15013664552,13530772012,13875986706"
				+ "18665330913,13926560513,13430552874,15659896712,13760688606,13662276478,15889294995,15019241799,18219208860";
		params.put("SpCode", "204581");
		params.put("LoginName", "admin");
		params.put("Password", "851111");
		String content = URLEncoder.encode("测试", "GB2312");
		System.out.println(content);
		params.put("MessageContent", "欢迎使用华侨城智慧景区免费无线服务！您本次登录的验证码为XXXXXX。本信息免费。");
		params.put("UserNumber", mobiles);
		params.put("SerialNumber", System.currentTimeMillis() + "");
		params.put("ScheduleTime", "");
		params.put("f", "1");
		HttpProcess process = new HttpProcess();
		byte[] bytes = process.doPost("http://guangdong.ums86.com:8899/sms/Api/Send.do", params);
		String responseBody = new String(bytes, "GBK");
		System.out.println(responseBody);
	}
	
	
}

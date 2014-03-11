package com.mopon.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * @date 2013年9月26日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("cookieManager")
public class CookieManager {
	
	/**
	 * COOKIE半小时常量
	 */
	public static final int HALF_HOUR = 1800;

	/**
	 * COOKIE小时常量
	 */
	public static final int HOUR = 3600;
	
	/**
	 * COOKIE天常量
	 */
	public static final int DAY = 86400;

	/**
	 * COOKIE周常量
	 */
	public static final int WEEK = 604800;

	/**
	 * COOKIE月常量
	 */
	public static final int MONTH = 2592000;

	/**
	 * COOKIE半年常量
	 */
	public static final int HALE_YEAR = 15552000;

	/**
	 * COOKIE年常量
	 */
	public static final int YEAR = 31104000;

	/**
	 * 方法用途: 写多个COOKIE<br>
	 * 实现步骤: <br>
	 * @param cookieMap COOKIE值MAP
	 * @param expiry 超时时间
	 * @param response 响应对象
	 */
	public void writeCookies(Map<String, String> cookieMap, int expiry, HttpServletResponse response) {
		for (String key : cookieMap.keySet()) {
			writeCookie(key, cookieMap.get(key), expiry, response);
		}
	}

	/**
	 * 方法用途: 写COOKIE<br>
	 * 实现步骤: <br>
	 * @param key 键
	 * @param value 值
	 * @param expiry 超时时间
	 * @param response 响应对象
	 */
	public void writeCookie(String key, String value, int expiry, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		if (expiry > 0) {
			cookie.setMaxAge(expiry);
		}
		response.addCookie(cookie);
	}

	/**
	 * 方法用途: 读所有COOKIE方法<br>
	 * 实现步骤: <br>
	 * @param request 请求对象
	 * @return
	 */
	public Map<String, String> readCookies(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieMap;
	}

	/**
	 * 方法用途: 读单独COOKIE值方法<br>
	 * 实现步骤: <br>
	 * @param key 键
	 * @param request 请求对象
	 * @return
	 */
	public String readCookie(String key, HttpServletRequest request) {
		Map<String, String> cookieMap = this.readCookies(request);
		return cookieMap.get(key);
	}
	
	
	/**
	 * 
	 * 方法用途: 删除COOKIE<br>
	 * @param key 键
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public void removeCookie(String key, HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies=request.getCookies();
		if(cookies == null){
			return;
		}
		for(Cookie cookie: cookies){
			if(key.equals(cookie.getName()))      
		    {      
				 cookie = new Cookie(cookie.getName(),null);
		         cookie.setMaxAge(0);
		         cookie.setPath("/");
		         response.addCookie(cookie);    
		    }  
		} 
	    
	}

}

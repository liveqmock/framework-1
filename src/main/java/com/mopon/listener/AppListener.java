package com.mopon.listener;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * <p>Description: 服务器过滤</p>
 * @date 2013年9月26日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class AppListener implements ServletContextListener {
	/**
	 * 绑定请求路径
	 */
    public final static String path ="http://127.0.0.1:8080/";

    /**
     * Default constructor. 
     */
    public AppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * 方法用途: 设置请求路径<br>
	 * 实现步骤: <br>
	 * @param context 应用上下文
	 */
    public void contextInitialized(ServletContextEvent context) {
    	context.getServletContext().setAttribute("rootpath",path);
    	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent context) {
        // TODO Auto-generated method stub
    }
	
}

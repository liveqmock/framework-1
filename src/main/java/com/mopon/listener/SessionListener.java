package com.mopon.listener;

import com.mopon.util.Session;

/**
 * <p>Description: SESSION事件</p>
 * @date 2013年9月25日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface SessionListener {
	
	/**
	 * 方法用途: 创建SESSION<br>
	 * 实现步骤: <br>
	 * @param session
	 */
	public void sessionCreated(Session session);
	
	/**
	 * 方法用途: 摧毁SESSION<br>
	 * 实现步骤: <br>
	 * @param session
	 */
	public void sessionDestroyed(Session session);
}

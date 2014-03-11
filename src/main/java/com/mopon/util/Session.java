package com.mopon.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mopon.dao.sys.ICachedDao;
import com.mopon.listener.SessionListener;



@Component("session")
public class Session implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2378099788138836460L;


	private static Map<String, Object> container = new HashMap<String, Object>();
	
	
	/**
	 * 过期时间为-1时不过期
	 */
	private long maxInactiveInterval = -1;
	
	/**
	 * 活动时间
	 */
	private long activitiTime = 0;
	
	/**
	 * sessionID
	 */
	private String sessionId;
	
	/**
	 * 事件句柄
	 */
	private SessionListener listener = null;
	
	/**
	 * 缓存
	 */
	@Autowired
	protected ICachedDao cachedDao;
	
	/**
	 * 方法用途: 初始化SESSION<br>
	 * 实现步骤: <br>
	 * @param sessionId 对话唯一值
	 */
	public void init(String sessionId) {
		this.sessionId = sessionId;
		//session事件不为空时处理
		if(listener != null) {
			//创建SESSION
			listener.sessionCreated(this);
		}
		this.activitiTime = System.currentTimeMillis();
	}
	
	/**
	 * 方法用途: 存入SESSION值<br>
	 * 实现步骤: <br>
	 * @param key 键
	 * @param value 值
	 */
	public void setAttribute(String key, Object value) {
		container.put(key, value);
	}
	
	/**
	 * 方法用途: 获取SESSION值<br>
	 * 实现步骤: <br>
	 * @param key 键
	 * @return 返回包含的值对象，出错时返回空
	 */
	public Object getAttribute(String key) {
		return container.get(key);
	}
	
	/**
	 * 方法用途: 删除对应的SESSION值<br>
	 * 实现步骤: <br>
	 * @param key 键
	 */
	public void removeSession(String key) {
		container.remove(key);
	}
	
	/**
	 * 方法用途: 清楚SESSION<br>
	 * 实现步骤: <br>
	 */
	public void clearSession() {
		container.clear();
	}
	
	/**
	 * 方法用途: 摧毁SESSION<br>
	 * 实现步骤: <br>
	 */
	public void sessionDestroy() {
		if(listener != null) {
			//销毁SESSION
			listener.sessionDestroyed(this);
		}
		container.clear();
		container = null;
		listener = null;
	}
	
	public long getActivitiTime() {
		return activitiTime;
	}

	public void setActivitiTime(long activitiTime) {
		this.activitiTime = activitiTime;
	}

	public void setMaxInactiveInterval(long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
	public long getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void addSessionListener(SessionListener listener) {
		this.listener = listener;
	}

	public SessionListener getListener() {
		return listener;
	}

}

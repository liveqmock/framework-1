package com.mopon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * <p>Description: session管理类</p>
 * @date 2013年9月25日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Scope("singleton")
@Component("sessionManager")
public class SessionManager {
	
	/**
	 * sessionMap存储SESSION
	 */
	private static Map<String, Session> sessionMap = null;
	
	
	/**
	 * 缓存
	 */
	@Autowired
	private MemcachedClient memcachedClient;

	/**
	 * 线程对象
	 */
	private static ExecutorService exs = null;
	
	public SessionManager() {
	}
	
	/**
	 * 方法用途: 创建SESSION<br>
	 * 实现步骤: <br>
	 * @param sessionId 会话唯一ID
	 * @return 返回SESSIO对象
	 */
	public Session getSession(String sessionId){
		Session session = null;
		try {
			sessionMap = (Map<String, Session>) memcachedClient.get("SESSIONMAP");
			if(sessionMap == null) {
				sessionMap = new ConcurrentHashMap<String, Session>();
			}
			//每个SESSIONID创建一个实例
			session = sessionMap.get(sessionId);
			if(session == null) {
				session = new Session();
				session.init(sessionId);
				sessionMap.put(sessionId, session);
			}
			memcachedClient.set("SESSIONMAP", 0, sessionMap);			
			
			exs = Executors.newSingleThreadExecutor();
			if(!exs.isShutdown()) {
				exs.execute(new SessionTask());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	
	class SessionTask implements Runnable {

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				//System.out.println("clear time out");
				List<String> clearList = new ArrayList<String>();
				try {
					sessionMap = (Map<String, Session>) memcachedClient.get("SESSIONMAP");
					for(String sessionId : sessionMap.keySet()) {
						Session session = sessionMap.get(sessionId);
						if(session.getMaxInactiveInterval() != -1) {
							long currentTime = System.currentTimeMillis();
							long activitiTime = session.getActivitiTime();
							//SESSION过期时清除
							if((currentTime - activitiTime) >=  session.getMaxInactiveInterval()) {
								session.sessionDestroy();
								//记录清除的ID
								clearList.add(sessionId);
							}
						}
					}
					for(String sessionId : clearList) {
						//清除MAP信息
						sessionMap.remove(sessionId);
					}
					memcachedClient.set("SESSIONMAP", 0, sessionMap);
					Thread.sleep(10000);
					} catch (Exception e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
				}
			
			}
	}
	
}

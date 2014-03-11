package com.mopon.service.sys;

/**
 * <p>Description: 初始化BEAN接口</p>
 * @date 2013年9月2日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ISysInitService {
	/**
	 * 方法用途: 系统初始化<br>
	 * 实现步骤: 实现该接口的BEAN将在系统启动时调用该方法进行初始化<br>
	 * @throws Exception
	 */
	public void initializer() throws Exception;
}

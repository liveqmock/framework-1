package com.mopon.listener;
/**
 * <p>Description: FTP进度消息事件接口</p>
 * @date 2013年9月4日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface FtpUploadListener {
	
	/**
	 * 方法用途: 进度响应更新方法<br>
	 * 实现步骤: <br>
	 * @param key 对应的键值
	 * @param progressvalue 进度值
	 */
	public void progress(String key, int progressvalue);
}

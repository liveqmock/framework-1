package com.mopon.timer;

/**
 * <p>Description: 定时任务接口</p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ITaskHandler {

	/**
	 * 方法用途: 执行<br>
	 * 实现步骤: 在定时器执行时调用该接口的方法处理定时任务<br>
	 */
	public void execute();
}

package com.mopon.timer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



import com.mopon.util.LoggerUtil;
/**
 * <p>Description: 序列化定时任务</p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class SerializableTaskJob implements Job {
	
	/**
	 * 定时任务队列
	 */
	private static List<ITaskHandler> taskHandlerList = new CopyOnWriteArrayList<ITaskHandler>();
	
	/**
	 * 日志类
	 */
	private LoggerUtil loggerUtil = new LoggerUtil(SerializableTaskJob.class);

	/**
	 * 方法用途: 加入事件处理类<br>
	 * 实现步骤: <br>
	 * @param handler
	 */
	public static void addTaskHandler(ITaskHandler handler) {
		taskHandlerList.add(handler);
	}

	/**
	 * 方法用途: 执行任务<br>
	 * 实现步骤: 调用方法执行任务<br>
	 * @param context 任务上下文
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		loggerUtil.info("==========SerializableTaskJob开始==========");
		if(taskHandlerList != null) {
			loggerUtil.info("==========当前任务数 :" + taskHandlerList.size() + "==========");
			for(int i = 0; i < taskHandlerList.size(); i++) {
				ITaskHandler handler = taskHandlerList.get(i);
				handler.execute();
			}
		}
		loggerUtil.info("==========SerializableTaskJob结束==========");
	}

}

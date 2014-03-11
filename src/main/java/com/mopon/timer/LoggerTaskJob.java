package com.mopon.timer;

import org.springframework.beans.factory.annotation.Autowired;

import com.mopon.asyn.LoggerAsynWrite;
import com.mopon.util.LoggerUtil;
/**
 * <p>Description:日志定时入库 </p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class LoggerTaskJob {
	
	/**
	 * 异步写日志方法
	 */
	@Autowired
	private LoggerAsynWrite loggerAsynWrite;
	
	/**
	 *  log4j工具
	 */
	@Autowired
	private LoggerUtil loggerUtil; 
	
	/**
	 * 方法用途: 定时调用入库<br>
	 * 实现步骤: <br>
	 */
	public void execute() {
		loggerUtil.info("==========日志入库开始==========");
		loggerAsynWrite.writer();
		loggerUtil.info("==========日志入库结束==========");
	}
}

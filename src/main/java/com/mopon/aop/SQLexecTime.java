package com.mopon.aop;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.mopon.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年12月23日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Aspect
@Component("SQLexecTime")
public class SQLexecTime {

	private LoggerUtil logger = new LoggerUtil(SQLexecTime.class);
	
	private long beginTime = 0;
	
	private long endTime = 0;
	
	@Before("execution(* com.mopon.dao..*.*(..))") 
	public void Before() throws Throwable {
		logger.info("===================SQLEXECTIME START========================");
		beginTime = System.currentTimeMillis();
	}
	
	@After("execution(* com.mopon.dao..*.*(..))")  
	public void After() throws Throwable {
		logger.info("===================SQLEXECTIME END========================");
		endTime = System.currentTimeMillis();
		logger.info("当前SQL语句执行时间为：" + (endTime - beginTime) + "毫秒");
	}
	
}

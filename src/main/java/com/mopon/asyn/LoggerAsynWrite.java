package com.mopon.asyn;



import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.service.sys.IErrorMsgService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.util.LoggerUtil;
import com.mopon.util.TypeFormat;

/**
 * <p>Description:日志异步入库类，将异常与操作日志定时入库</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Scope("singleton")
@Component("loggerAsynWrite")
public class LoggerAsynWrite {
	
	/**
	 * 错误缓存
	 */
	private static List<ErrorMsg> errorMsgCache = new CopyOnWriteArrayList<ErrorMsg>();
	
	/**
	 * 日志缓存
	 */
	private static List<OperateMsg> operateMsgCache = new CopyOnWriteArrayList<OperateMsg>(); 
	
	/**
	 * 错误信息日志服务类
	 */
	@Autowired
	private IErrorMsgService errorMsgService;
	
	/**
	 * 操作日志类
	 */
	@Autowired
	private IOperateMsgService operateMsgService;
	
	/**
	 * 类型格式化类
	 */
	@Autowired
	private TypeFormat typeFormat;
	
	/**
	 * 日志类
	 */
	@Autowired
	private LoggerUtil loggerUtil; 
	
	/** 
	 * 方法用途: 将错误加入缓存
	 * 实现步骤: 错误对象放入到缓存中，到一定量的入库清除<br>
	 * @param:ErrorMsg错误日志对象
	 */
	public static void addExceptionQueue(ErrorMsg errorMsg) {
		errorMsgCache.add(errorMsg);
	}
	
	/** 
	 * 方法用途: 将日志加入缓存
	 * 实现步骤: 日志对象放入到缓存中，到一定量的时候入库清除<br>
	 * @param:OperateMsg操作日志对象
	 */
	public static void addOperateQueue(OperateMsg operateMsg) {
		operateMsgCache.add(operateMsg);
	}
	
	/** 
	 * 方法用途: 日志入库
	 * 实现步骤: 日志从缓存中读取出来放入到数据库中<br>
	 */
	public void writer() {
		try {
			loggerUtil.info("每5分钟入库一次信息");
			if(errorMsgCache.size() != 0) {
				loggerUtil.info("错误信息入库");
				String collectionName = "errorMsg" + typeFormat.fromatMother(new Date());
				errorMsgService.createTable(collectionName);
				errorMsgService.addBatchErrorMsg(collectionName, errorMsgCache);
				errorMsgCache.clear();
			}
			if(operateMsgCache.size() != 0) {
				loggerUtil.info("操作信息入库");
				operateMsgService.addBatchOperateMsg(operateMsgCache);
				operateMsgCache.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}

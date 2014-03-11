package com.mopon.service.sys;

import java.util.List;

import com.mopon.entity.logs.Logging;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.OperateException;

/**
 * <p>Description: </p>
 * @date 2013年12月25日
 * @author 王丽松
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ILoggingService {

	public void addBatchLogging(List<Logging> loggingList);
	
	public PageBean<Logging> queryLoggingForList(Logging logging, int page, int pageCount) throws OperateException, DatabaseException;

	public void addLogging(Logging logging);
}

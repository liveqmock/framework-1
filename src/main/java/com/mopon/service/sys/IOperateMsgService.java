package com.mopon.service.sys;

import java.util.List;

import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.OperateException;

/**
 * <p>Description: </p>
 * @date 2013年8月28日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IOperateMsgService {

	public void addBatchOperateMsg(List<OperateMsg> operateMsgList);
	
	public PageBean<OperateMsg> queryOperateMsgForList(OperateMsg operateMsg, int page, int pageCount) throws OperateException, DatabaseException;
}

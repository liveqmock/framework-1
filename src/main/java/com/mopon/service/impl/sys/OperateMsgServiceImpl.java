package com.mopon.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.logs.ILoggerDao;
import com.mopon.entity.logs.OPType;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.OperateException;
import com.mopon.service.sys.IOperateMsgService;


/**
 * <p>Description: </p>
 * @date 2013年8月28日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("operateMsgService")
public class OperateMsgServiceImpl extends BaseServiceImpl implements IOperateMsgService {
	
	@Autowired
	private ILoggerDao loggerDao;


	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param operateMsg   
	 */
	@Override
	@Transactional
	public void addBatchOperateMsg(List<OperateMsg> operateMsgList) {
		loggerDao.saveBatch(operateMsgList);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param collectionName
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws OperateException
	 * @throws DatabaseException   
	 */
	@Override
	@Transactional
	public PageBean<OperateMsg> queryOperateMsgForList(OperateMsg operateMsg, int page, int pageCount) throws OperateException, DatabaseException {
		
		PageBean<OperateMsg> pageBean = new PageBean<OperateMsg>();
		pageBean.setCurrentPage(page);
		if(page > 0) {
			page = page - 1;
		}
		int start = page * pageCount;
		Integer count = loggerDao.getOperateMsgCount(operateMsg, operateMsg.getStartDate(), operateMsg.getEndDate());
		
		List<OperateMsg> operateMsgList =  loggerDao.queryOperateMsgForList(operateMsg, operateMsg.getStartDate(), operateMsg.getEndDate(), start, pageCount);
		for(OperateMsg o:operateMsgList){
			switch(o.getOpType()){
			case 0:
				o.setOpName("添加操作");
				break;
			case 1:
				o.setOpName("删除操作");
				break;
			case 2:
				o.setOpName("更新操作");
				break;
			case 3:
				o.setOpName("查询操作");
				break;
			case 4:
				o.setOpName("登录系统");
				break;
			case 5:
				o.setOpName("登出系统");
				break;
			default:
				o.setOpName("未定义");
				break;
			}
		}
		pageBean.setRecordCount(count);
		pageBean.setPageSize(pageCount);
		pageBean.setDataList(operateMsgList);
		return pageBean;
	}

}

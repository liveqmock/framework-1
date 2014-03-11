package com.mopon.service.impl.sys;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mopon.dao.master.logs.ILoggingDao;
import com.mopon.entity.logs.Logging;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.OperateException;
import com.mopon.service.sys.ILoggingService;


/**
 * <p>Description: </p>
 * @date 2013年12月25日
 * @author 王丽松
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("loggingService")
public class LoggingServiceImpl extends BaseServiceImpl implements ILoggingService {
	
	@Autowired
	private ILoggingDao loggingDao;

	@Override
	public void addBatchLogging(List<Logging> loggingList) {
		loggingDao.saveBatch(loggingList);
	}
	
	@Override
	public void addLogging(Logging logging) {
		loggingDao.insertSelective(logging);
	}

	@Override
	public PageBean<Logging> queryLoggingForList(Logging logging, int page, int pageCount) 
			throws OperateException, DatabaseException {
		PageBean<Logging> pageBean = new PageBean<Logging>();
		pageBean.setCurrentPage(page);
		if(page > 0) {
			page = page - 1;
		}
		int start = page * pageCount;
		Integer count = loggingDao.getLoggingCount(logging, logging.getStartDate(), logging.getEndDate());
		List<Logging> loggingList =  loggingDao.queryLoggingForList(logging, logging.getStartDate(), logging.getEndDate(), start, pageCount);
		pageBean.setRecordCount(count);
		pageBean.setPageSize(pageCount);
		pageBean.setDataList(loggingList);
		return pageBean;
	}
}

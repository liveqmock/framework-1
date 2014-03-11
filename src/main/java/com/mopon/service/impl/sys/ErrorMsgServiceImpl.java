package com.mopon.service.impl.sys;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.ErrorMsgException;
import com.mopon.service.sys.IErrorMsgService;

@Service("errorMsgService")
public class ErrorMsgServiceImpl extends BaseServiceImpl implements IErrorMsgService {
	
	public void createTable(String collectionName) {
		this.repositoryDao.createCollection(collectionName);
	}
	
	@Override
	@Transactional
	public void addBatchErrorMsg(String collectionName, List<ErrorMsg> errorMsgList) throws ErrorMsgException {
		for(ErrorMsg errorMsg : errorMsgList) {
			this.repositoryDao.save(collectionName, errorMsg);
		}
	}
	
	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param collectionName
	 * @param eid
	 * @return
	 * @throws Exception   
	 */
	@Override
	public ErrorMsg getErrorMsgDetail(String collectionName, String eid) throws ErrorMsgException {
		return (ErrorMsg) this.repositoryDao.findById(collectionName, ErrorMsg.class, eid);
	}
	
	

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param errorMsg
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws Exception   
	 */
	@Override
	public PageBean<ErrorMsg> queryErrorMsgForList(String collectionName, ErrorMsg errorMsg, int pageNo, int pageCount) throws ErrorMsgException {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(validatorUtil.isNotEmpty(errorMsg.getEid())) {
			criteria = Criteria.where("eid").is(errorMsg.getEid());
			query.addCriteria(criteria);
		}
		if(errorMsg.getCode() != 0) {
			criteria = Criteria.where("code").is(errorMsg.getCode() );
		}
		if(validatorUtil.isNotObjectNull(errorMsg.getErrorDate())) {
			criteria = Criteria.where("errorDate").lte(errorMsg.getErrorDate()).gte(errorMsg.getErrorDate());
		}
		if(validatorUtil.isNotEmpty(errorMsg.getMsg())) {
			Pattern pattern = Pattern.compile("^.*"  + errorMsg.getMsg()+  ".*$" , Pattern.CASE_INSENSITIVE);   
			criteria = Criteria.where("msg").regex(pattern);
		}
		PageBean<ErrorMsg> pageBean = new PageBean<ErrorMsg>();
		pageBean.setCurrentPage(pageNo);
		if(pageNo > 0) {
			pageNo = pageNo - 1;
		}
		
		long count = this.repositoryDao.findCount(collectionName, query);
		
		int startIndex = pageNo * pageCount;
		
		List<ErrorMsg> errorMsgList =  (List<ErrorMsg>) this.repositoryDao.findList(collectionName, ErrorMsg.class, query, startIndex, pageCount);

		pageBean.setPageCount(count);
		pageBean.setPageSize(pageCount);
		pageBean.setDataList(errorMsgList);
		
		return pageBean;
	}

	
}

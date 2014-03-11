package com.mopon.service.sys;


import java.util.List;

import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.ErrorMsgException;

public interface IErrorMsgService {
	
	public void createTable(String collectionName);
	
	public void addBatchErrorMsg(String collectionName, List<ErrorMsg> erroMsgList) throws ErrorMsgException;
	
	public ErrorMsg getErrorMsgDetail(String collectionName, String eid) throws ErrorMsgException;

	public PageBean<ErrorMsg> queryErrorMsgForList(String collectionName, ErrorMsg errorMsg, int page, int pageCount) throws ErrorMsgException;
}

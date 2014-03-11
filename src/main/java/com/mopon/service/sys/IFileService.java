package com.mopon.service.sys;

import com.mopon.entity.sys.FileMessage;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.FileException;

/**
 * <p>Description: </p>
 * @date 2013年9月24日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IFileService {
	
	public void addFile(FileMessage fileMessage) throws FileException;
	
	public FileMessage getFileMessageByID(String fid) throws FileException;
	
	public void removeFile(FileMessage fileMessage) throws FileException;
	
	public FileMessage getFileMessageByFid(String fid) throws FileException;
	
	public PageBean<FileMessage> queryFileMessagForList(FileMessage fileMessage, int pageNo, int pageCount) throws FileException;

}

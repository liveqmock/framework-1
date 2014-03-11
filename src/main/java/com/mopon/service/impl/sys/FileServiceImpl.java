package com.mopon.service.impl.sys;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.entity.sys.FileMessage;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.FileException;
import com.mopon.service.sys.IFileService;

/**
 * <p>Description: 文件管理类</p>
 * @date 2013年9月24日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("fileService")
public class FileServiceImpl extends BaseServiceImpl implements IFileService {
	
	private final String collectionName = "FileMessage";
	
	public void createTable(String collectionName) {
		this.repositoryDao.createCollection(collectionName);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param fileMessage
	 * @throws FileException   
	 */
	@Override
	@Transactional
	public void addFile(FileMessage fileMessage) throws FileException {
		repositoryDao.save(collectionName, fileMessage);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param fid
	 * @throws FileException   
	 */
	@Override
	public FileMessage getFileMessageByID(String fid) throws FileException {
		return (FileMessage) repositoryDao.findById(collectionName, FileMessage.class, fid);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param fid
	 * @throws FileException   
	 */
	@Override
	public FileMessage getFileMessageByFid(String fid) throws FileException {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria = Criteria.where("fid").is(fid);
		query.addCriteria(criteria);
		return (FileMessage) repositoryDao.findOne(collectionName, FileMessage.class, query);
	}
	
	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param fid
	 * @throws FileException   
	 */
	@Override
	@Transactional
	public void removeFile(FileMessage fileMessage) throws FileException {
		repositoryDao.remove(collectionName, fileMessage);
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param fileMessage
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @return
	 * @throws Exception   
	 */
	@Override
	public PageBean<FileMessage> queryFileMessagForList(FileMessage fileMessage, int pageNo, int pageCount)
			throws FileException {
		Query query = new Query();
		Criteria criteria = new Criteria();
		if(validatorUtil.isNotEmpty(fileMessage.getFid())) {
			criteria = Criteria.where("fid").is(fileMessage.getFid());
			query.addCriteria(criteria);
		}
		if(validatorUtil.isNotEmpty(fileMessage.getFilename())) {
			Pattern pattern = Pattern.compile("^.*"  + fileMessage.getFilename() +  ".*$" , Pattern.CASE_INSENSITIVE);  
			criteria = Criteria.where("filename").regex(pattern);
		}
		if(validatorUtil.isNotEmpty(fileMessage.getType())) {
			criteria = Criteria.where("type").is(fileMessage.getType());
			query.addCriteria(criteria);
		}
		if(validatorUtil.isNotEmpty(fileMessage.getUpType())) {
			criteria = Criteria.where("upType").is(fileMessage.getUpType());
			query.addCriteria(criteria);
		}
		PageBean<FileMessage> pageBean = new PageBean<FileMessage>();
		pageBean.setCurrentPage(pageNo);
		if(pageNo > 0) {
			pageNo = pageNo - 1;
		}
		
		long count = this.repositoryDao.findCount(collectionName, query);
		
		int startIndex = pageNo * pageCount;
		
		List<FileMessage> fileMessageList =  (List<FileMessage>) this.repositoryDao.findList(collectionName, FileMessage.class, query, startIndex, pageCount);

		pageBean.setPageCount(count);
		pageBean.setPageSize(pageCount);
		pageBean.setDataList(fileMessageList);
		
		return pageBean;
	}

}

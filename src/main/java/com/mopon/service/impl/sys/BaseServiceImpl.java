package com.mopon.service.impl.sys;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mopon.asyn.LoggerAsynWrite;
import com.mopon.component.Component;
import com.mopon.dao.sys.ICachedDao;
import com.mopon.dao.sys.IRepositoryDao;
import com.mopon.entity.logs.OperateCode;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.sys.MessageEntity;
import com.mopon.jms.QueueMessageProducer;
import com.mopon.jms.TopicMessageProducer;
import com.mopon.jms.impl.MessageMdp;

public class BaseServiceImpl extends Component {
	
	@Autowired
	protected ICachedDao cachedDao;
	
	@Autowired
	protected IRepositoryDao repositoryDao;
	
	/**
	 * JMS订阅消息
	 */
	@Autowired
	private TopicMessageProducer topicMessageProducer;

	/**
	 * JMS队列消息
	 */
	@Autowired
	private QueueMessageProducer queueMessageProducer;
	
	/**
	 * JMS订阅接受
	 */
	@Autowired
	protected MessageMdp topicMessageMdp;
	
	/**
	 * JMS队列接受
	 */
	@Autowired
	protected MessageMdp queueMessageMdp;
	
	/** 
	 * 方法用途: 发送JMS消息
	 * 实现步骤: 发送JMS消息方法，该方法是对JMS的统一包装<br>
	 * @return String 字符串唯一键值
	 */
	public void sendJMSMessage(MessageEntity messageEntity, boolean isTopic) {
		if (isTopic) {
			topicMessageProducer.send(messageEntity);
		} else {
			queueMessageProducer.send(messageEntity);
		}
	}
	
	/** 
	 * 方法用途: 记录操作日志
	 * 实现步骤: 每次进行操作的信息都将保存入数据库<br>
	 * @param msg操作信息
	 * @param 作者信息
	 * @param 作者ID
	 * @param 操作类型
	 */
	public void operateLogger(int uid, String name, int siteId, String siteName, String message) {
		OperateMsg operateMsg = new OperateMsg();
		operateMsg.setUid(uid);
		operateMsg.setName(name);
		operateMsg.setSiteId(siteId);
		operateMsg.setSiteName(siteName);
		operateMsg.setDateline(new Date());
		operateMsg.setMessage(message);
		LoggerAsynWrite.addOperateQueue(operateMsg);
	}

	
}

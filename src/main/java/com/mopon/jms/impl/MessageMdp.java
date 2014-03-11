package com.mopon.jms.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.sys.MessageEntity;
import com.mopon.exception.MessageException;
import com.mopon.listener.JmsMessageListener;
import com.mopon.util.JSONTools;

/**
 * <p>Description: 消息接收类</p>
 * @date 2013年9月4日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MessageMdp implements MessageListener {
	
	/**
	 * 接收事件管理
	 */
	private List<JmsMessageListener> listeners = new ArrayList<JmsMessageListener>();
	
	/**
	 * 方法用途: 设置接收时间<br>
	 * 实现步骤: <br>
	 * @param listener 事件接口实现
	 */
	public void setListener(JmsMessageListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * 方法用途: 删除事件<br>
	 * 实现步骤: <br>
	 * @param listener
	 */
	public void removeListener(JmsMessageListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * 方法用途: 清除所有事件<br>
	 * 实现步骤: <br>
	 * @param listener
	 */
	public void clearListener() {
		listeners.clear();
	}
	
	/** 
	 * 方法用途: 接收消息方法<br>
	 * 实现步骤: <br>
	 * @param message 接收的消息
	 */
	@Override
	public void onMessage(Message message) {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			if (message instanceof ObjectMessage) {
				String objectJson = ((ObjectMessage) message).getStringProperty("JSON");
				JSONTools<MessageEntity> json = new JSONTools<MessageEntity>();
				MessageEntity messageEntity = json.readObject(objectJson, MessageEntity.class);
				for(JmsMessageListener listener : listeners) {
					listener.receiveMsg(messageEntity);
				}
			}
		} catch (Exception e) {
			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setEid(UUID.randomUUID().toString());
			errorMsg.setCode(ErrorStatus.JMS_REVICE.value());
			errorMsg.setErrorDate(new Date());
			errorMsg.setMsg(e.getMessage());
			errorMsg.setType(ErrorType.SYSTEM_ERROR.value());
			new MessageException(errorMsg);
		} finally {
			try {
				ois.close();
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

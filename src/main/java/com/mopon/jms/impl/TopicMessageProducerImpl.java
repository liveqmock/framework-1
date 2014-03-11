package com.mopon.jms.impl;

import java.util.Date;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.sys.MessageEntity;
import com.mopon.exception.MessageException;
import com.mopon.jms.TopicMessageProducer;
import com.mopon.util.JSONTools;

/**
 * <p>Description: 发送主题消息类</p>
 * @date 2013年9月4日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("topicMessageProducer")
public class TopicMessageProducerImpl implements TopicMessageProducer {

	/**
	 * 消息模板
	 */
	private JmsTemplate template;

	/**
	 * 消息类型
	 */
	private Topic destination;

	/**
	 * 方法用途: 设置消息模板<br>
	 * 实现步骤: <br>
	 * @param template 消息模板对象
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	/**
	 * 方法用途: 设置消息目标对象<br>
	 * 实现步骤: <br>
	 * @param destination 消息目标对象
	 */
	public void setDestination(Topic destination) {
		this.destination = destination;
	}
	
	/**
	 * 方法用途: 发送消息<br>
	 * 实现步骤: <br>
	 * @param messageEntity 消息内容包装类
	 */
	public void send(final MessageEntity<?> messageEntity) {
		template.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message msg = session.createObjectMessage();
				try {
					JSONTools<MessageEntity<?>> json = new JSONTools<MessageEntity<?>>();
					String objectJson = json.writeObject(messageEntity);
					msg.setStringProperty("JSON", objectJson);
				} catch (Exception e) {
					e.printStackTrace();
					ErrorMsg errorMsg = new ErrorMsg();
					errorMsg.setEid(UUID.randomUUID().toString());
					errorMsg.setCode(ErrorStatus.JMS_TOPIC_SEND.value());
					errorMsg.setErrorDate(new Date());
					errorMsg.setMsg(e.getMessage());
					errorMsg.setType(ErrorType.SYSTEM_ERROR.value());
					new MessageException(errorMsg);
				} 
				return msg;
			}
			
		});
	}
	
}

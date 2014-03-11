package com.mopon.jms;

import com.mopon.entity.sys.MessageEntity;

public interface TopicMessageProducer {
	
	public void send(MessageEntity<?> message);
}

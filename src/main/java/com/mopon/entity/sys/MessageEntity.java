package com.mopon.entity.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MessageEntity<T> implements Serializable{

	private static final long serialVersionUID = 8053606994230813904L;

	private int msgId;
	
	private Map<String, String> header;
	
	private int type;
	
	private String textBody;
	
	private Object objectBody;
	
	private byte[] byteBody;
	
	private List<T> arrayBody;

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

	public Object getObjectBody() {
		return objectBody;
	}

	public void setObjectBody(Object objectBody) {
		this.objectBody = objectBody;
	}

	public byte[] getByteBody() {
		return byteBody;
	}

	public void setByteBody(byte[] byteBody) {
		this.byteBody = byteBody;
	}

	public List<T> getArrayBody() {
		return arrayBody;
	}

	public void setArrayBody(List<T> arrayBody) {
		this.arrayBody = arrayBody;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

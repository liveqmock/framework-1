package com.mopon.entity.sys;

import java.util.Date;

/**
 * <p>Description: 系统配置字典</p>
 * @date 2013年10月11日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class Config {

	private String key;
	
	private String value;
	
	private Date date;
	
	private String keyDesc;
	
	private String valueDesc;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getKeyDesc() {
		return keyDesc;
	}

	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}

	public String getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
}

package com.mopon.entity.logs;

import java.util.Date;
import java.util.UUID;


/**
 * 
 * <p>Description: 错误信息日志</p>
 * @date 2013年9月5日
 * @author rr
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ErrorMsg {
	
	/**
	 * 错误ID java.util.uuid生成字符串
	 */
	private String eid;
	
	/**
	 * 错误代码
	 */
	private int code;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	/**
	 * 错误日期
	 */
	private Date errorDate;
	
	/**
	 * 日志级别
	 */
	private int level;
	
	/**
	 * 错误类型
	 */
	private int type;
	
	
	private boolean success;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getErrorDate() {
		return errorDate;
	}

	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorMsg(){
		
	}
	public ErrorMsg(int code,String msg,int level,int type){
		this.eid = UUID.randomUUID().toString();
		this.code = code;
		this.msg = msg;
		this.level = level;
		this.type = type;
		this.errorDate = new Date();
	}
	
}

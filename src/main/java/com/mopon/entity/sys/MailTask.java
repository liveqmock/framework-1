package com.mopon.entity.sys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Description: 邮件任务对象</p>
 * @date 2013年10月28日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MailTask {
	/**
	 * 目标地址
	 */
	private String mailAddress;
	
	/**
	 * 主题
	 */
	private String subject;
	
	/**
	 * 发件人
	 */
	private String from;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 附件列表
	 */
	private List<File> attachments = new ArrayList<File>();
	
	private int againCount;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public int getAgainCount() {
		return againCount;
	}

	public void setAgainCount(int againCount) {
		this.againCount = againCount;
	}
	
}

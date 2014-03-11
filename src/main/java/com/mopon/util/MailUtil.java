package com.mopon.util;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mopon.entity.sys.MailTask;

/**
 * <p>Description: 发送邮件</p>
 * @date 2013年10月28日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MailUtil {
	
	/**
	 * 日志
	 */
	protected LoggerUtil loggerUtil = new LoggerUtil(MailUtil.class);
	
	/**
	 * 邮件服务器
	 */
	private JavaMailSender mailSender;
	
	/**
	 * 任务队列
	 */
	private static Queue<MailTask> mailTaskQueue = new ConcurrentLinkedQueue<MailTask>();
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * 方法用途: 邮件任务队列<br>
	 * 实现步骤: <br>
	 * @param mailTask 添加的邮件任务
	 */
	public void addMailTaskQueue(MailTask mailTask) {
		mailTaskQueue.add(mailTask);
	}
	
	/**
	 * 方法用途: 初始化邮件<br>
	 * 实现步骤: <br>
	 */
	public void init() {
		//创建邮件线程发送
		new Thread(new Send()).start();
		loggerUtil.info("发送邮件任务线程启动");
	}
	
	class Send implements Runnable {

		public void run() {
			while(!Thread.interrupted()) {
				//创建邮件
				MimeMessage mineMessage = mailSender.createMimeMessage();
				MailTask mailTask = null;
				//loggerUtil.info("带发送邮件任务：" + mailTaskQueue.size() + "个");
			
				try {
					//取队列中的任务
					for(int i = 0; i < mailTaskQueue.size(); i++) {
						mailTask = mailTaskQueue.remove();
						loggerUtil.info("发送邮件");
						//重发次数大于5时
						if(mailTask.getAgainCount() < 5) {
							MimeMessageHelper messageHelper = new MimeMessageHelper(mineMessage , true, "utf-8");
							//目的地
							messageHelper.setTo(mailTask.getMailAddress());
							//从什么地方发出
							messageHelper.setFrom(mailTask.getFrom());
							//主题
							messageHelper.setSubject(mailTask.getSubject());
							//内容
							messageHelper.setText(mailTask.getContent(), true);
							//添加附件
							if(mailTask.getAttachments() != null) {
								for(File file : mailTask.getAttachments()) {
									messageHelper.addAttachment(MimeUtility.decodeText(file.getName()), file);
								}
							}
							mailSender.send(mineMessage);
						} 
					}
				} catch (Exception e) {
					e.printStackTrace();
					//出现异常时重入队列
					mailTaskQueue.add(mailTask);
					int againCount = mailTask.getAgainCount();
					againCount++;
					mailTask.setAgainCount(againCount);
					loggerUtil.info("邮件发生异常，重新加入发送任务队列");
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

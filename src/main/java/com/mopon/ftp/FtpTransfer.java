package com.mopon.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;  


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mopon.entity.sys.FtpTask;
import com.mopon.listener.FtpUploadListener;
/**
 * <p>Description: </p>
 * @date 2013年9月4日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("ftpTransfer")
public class FtpTransfer {
	
	/**
	 * 日志
	 */
	private static Logger logger = Logger.getLogger(FtpTransfer.class);

	/**
	 * FTP工厂类
	 */
	@Autowired
	private FtpFactoryBean ftpFactoryBean;
	
	/**
	 * 线程池
	 */
    private ExecutorService executorService; 
	
	/**
	 * FTP上传消息事件类
	 */
	private FtpUploadListener listener;
	
	/**
	 * 方法用途: 返回消息事件<br>
	 * 实现步骤: <br>
	 * @return
	 */
	public FtpUploadListener getListener() {
		return listener;
	}

	/**
	 * 方法用途: 添加消息时间<br>
	 * 实现步骤: <br>
	 * @param listener 消息事件接口
	 */
	public void setListener(FtpUploadListener listener) {
		System.out.println(listener);
		this.listener = listener;
	}

	/**
	 * 方法用途: 添加任务<br>
	 * 实现步骤: <br>
	 * @param key 任务ID键
	 * @param ftpTask 任务对象
	 * @return 完成TRUE,未完成FALSE
	 * @throws Exception
	 */
	public boolean addFtpTask(String key, FtpTask ftpTask) throws Exception {
		boolean isSuccess = false;
		executorService = Executors.newFixedThreadPool(15);  
		Future<FtpTask> future = executorService.submit(new UploadTask(key, ftpTask));
		isSuccess = future.get().isEnd();
		return isSuccess;
	}
	
	/**
	 * <p>Description: 任务线程处理类</p>
	 * @date 2013年9月4日
	 * @author reagan
	 * @version 1.0
	 * <p>Company:Mopon</p>
	 * <p>Copyright:Copyright(c)2013</p>
	 */
	class UploadTask implements Callable<FtpTask> {
		
		private String key = null;
		
		private FtpTask task = null;
		
		public UploadTask(String key, FtpTask task) {
			this.key = key;
			this.task = task;
		}

		@Override
		public FtpTask call() throws Exception {
			FTPClient ftp = ftpFactoryBean.getFtpConnection();
			InputStream in = task.getInput();
			OutputStream out = ftp.storeFileStream(task.getFilename());
			long filesize = task.getFilesize();
			int blocksize = (int) (filesize / 100);
			byte[] buffer = new byte[4096];
			int progress = 0;
			int bytes = 0;
			int count = 0;
			while((bytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				count += bytes;
				if(count > blocksize) {
					progress += 1;
					count = 0;
					listener.progress(key, progress);
				}
			}
			progress += 1;
			listener.progress(key, progress);
			ftp.logout();
			logger.info("----------->>>文件上传成功");
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error("----------->>>ftp连接关闭失败 " + ioe.getMessage());
				}
			}
			task.setEnd(true);
			return task;
		}
	}
	
	
	/**
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            FTP服务器下载上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public boolean downloadFile(String filename, OutputStream out)
			throws Exception {
		boolean success = false;
		FTPClient ftp = ftpFactoryBean.getFtpConnection();
		InputStream input = ftp.retrieveFileStream(filename);
		System.out.println(filename);
		byte[] buffer = new byte[4096];
		while ((input.read(buffer)) != -1) {
			out.write(buffer);
		}
		out.flush();
		input.close();
		ftp.logout();
		logger.info("----------->>>文件下载成功");
		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException ioe) {
				logger.error("----------->>>ftp连接关闭失败 " + ioe.getMessage());
			}
		}
		return success;
	}
}

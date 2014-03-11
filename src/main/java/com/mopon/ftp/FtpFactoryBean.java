package com.mopon.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>Description: FTP工厂类</p>
 * @date 2013年9月4日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class FtpFactoryBean {
	
	/**
	 * 本地线程同步变量
	 */
	private static ThreadLocal<FTPClient> local = new ThreadLocal<FTPClient>();
	
	/**
	 * FTP地址
	 */
	private String url;
	
	/**
	 * FTP端口
	 */
	private int port;
	
	/**
	 * FTP路径
	 */
	private String path;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 方法用途: 构造函数<br>
	 * 实现步骤: 创建FTP<br>
	 * @param url 地址
	 * @param port 端口
	 * @param path 保存路径
	 * @param username 用户名
	 * @param password 密码
	 */
	public FtpFactoryBean(String url, int port, String path, String username, String password) {
		this.url = url;
		this.port = port;
		this.path = path;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 方法用途: 建立FTP连接<br>
	 * 实现步骤: <br>
	 * @return FTP客户端
	 */
	public FTPClient getFtpConnection() {
		FTPClient ftp = local.get();
		if(ftp == null) {
			ftp = connect();
			local.set(ftp);
		}
		return ftp;
	}
	
	/**
	 * 方法用途: 建立连接<br>
	 * 实现步骤: <br>
	 * @return FTP客户端
	 */
	private FTPClient connect() {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(url, port);// 连接FTP服务器
			int reply;
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			// 设置PassiveMode传输
			ftp.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.out.println("----------->>>连接ftp服务器失败");
				throw new Exception("----------->>>连接ftp服务器失败");
			}
			System.out.println("-----连接ftp服务器成功");
			boolean isChangeWork = ftp.changeWorkingDirectory(path);
			if (!isChangeWork) {
				boolean isMade = ftp.makeDirectory(path);
				if (!isMade) {
					throw new IOException("ftp 上传文件穿件目录失败");
				}
				isChangeWork = ftp.changeWorkingDirectory(path);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ftp;
	}
	
	public  static void main(String[] args) {
		new FtpFactoryBean("127.0.0.1", 21, "/", "root", "123456").getFtpConnection();
	}
}

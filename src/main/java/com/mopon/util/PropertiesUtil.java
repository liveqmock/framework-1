package com.mopon.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Description: 读取配置文件</p>
 * @date 2013年10月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class PropertiesUtil {
	
	/**
	 * 读取配置文件对象
	 */
	private Properties proper; 
	
	/**
	 * 初始化类
	 * @param configName 配置文件地址路径与名称的组合
	 */
	public PropertiesUtil(String configName) {
		try {
			proper = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/" + configName);
			proper.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法用途: 获取配置信息<br>
	 * 实现步骤: <br>
	 * @param key 配置信息的键值
	 * @return
	 */
	public String getProperty(String key) {
		return proper.getProperty(key);
	}

}

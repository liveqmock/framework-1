package com.mopon.component;

import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;

import com.mopon.controller.httpclient.HttpProcess;
import com.mopon.util.Arith;
import com.mopon.util.Base64Utils;
import com.mopon.util.CookieManager;
import com.mopon.util.KeyGenerater;
import com.mopon.util.LoggerUtil;
import com.mopon.util.MD5;
import com.mopon.util.SessionManager;
import com.mopon.util.SignProvider;
import com.mopon.util.Signaturer;
import com.mopon.util.TypeFormat;
import com.mopon.util.ValidatorUtil;
import com.mopon.util.XmlDom;

/**
 * <p>Description:统一组件类，将各个需要的组件加入到该类中统一使用</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class Component {

	/**
	 * HTTP Client 采用RESTFUL方式发送请求
	 */
	public static final boolean RESTFUL = true;

	/**
	 * HTTP Client 采用传统键值对发送请求
	 */
	public static final boolean KEY_VALUE = false;
	
	/**
	 * HTTP Client 使用GET方式
	 */
	public static final boolean GET = true;

	/**
	 * HTTP Client 使用POST方式
	 */
	public static final boolean POST = false;
	
	/**
	 * TABLE 时间区间查询方式
	 */
	public static final String TABLE_CREADATE ="create_date";
	
	/**
	 * TABLE 名字模糊查询
	 */
	public static final String TABLE_NAME = "tableName";

	/**
	 * 日志记录
	 */
	@Autowired
	protected LoggerUtil loggerUtil;

	/**
	 * 公钥私钥生成工具
	 */
	@Autowired
	protected KeyGenerater keyGenerater;

	/**
	 * BASE64解密加密工具
	 */
	@Autowired
	protected Base64Utils base64Utils;

	/**
	 * MD5加密工具
	 */
	@Autowired
	protected MD5 md5;

	/**
	 * 数字签名
	 */
	@Autowired
	protected Signaturer signaturer;

	/**
	 * 数字签名
	 */
	@Autowired
	protected SignProvider signProvider;

	/**
	 * 货币包装
	 */
	@Autowired
	protected Arith arith;

	/**
	 * 类型转换
	 */
	@Autowired
	protected TypeFormat typeFormat;
	
	/**
	 * 字段验证
	 */
	@Autowired
	protected ValidatorUtil validatorUtil;
	
	
	/**
	 * XML生成对象
	 */
	@Autowired
	protected XmlDom xmlDom;
	

	/**
	 * SESSION
	 */
	@Autowired
	protected SessionManager sessionManager;
	
	
	/**
	 * SESSION
	 */
	@Autowired
	protected CookieManager cookieManager;

	/**
	 * HTTP请求发送
	 */
	@Autowired
	private HttpProcess httpProcess;
	

	
	/** 
	 * 方法用途: 返回字符串键值
	 * 实现步骤: 由UUID生成的字符串主键<br>
	 * @return String 字符串唯一键值
	 */
	public synchronized String getUUIDPrimarykey() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 方法用途: 发送HTTP请求<br>
	 * 实现步骤: <br>
	 * @param url 发送地址
	 * @param params 请求参数MAP
	 * @param method 采用函数
	 * @param type 请求类型
	 * @return
	 */
	public byte[] sendHttpRequest(String url, Map<String, String> params,
			boolean method, boolean type) {
		if (method) {
			return httpProcess.doGet(url, params, type);
		} else {
			return httpProcess.doPost(url, params);
		}
	}
}

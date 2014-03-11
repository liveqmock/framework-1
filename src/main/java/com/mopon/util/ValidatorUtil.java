package com.mopon.util;

/**
 * <p>Description: </p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component("validatorUtil")
public class ValidatorUtil {

	/**
	 * 判断是否为浮点数或者整数
	 * @param str
	 * @return true Or false
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为正确的邮件格式
	 * @param str
	 * @return boolean
	 */
	public boolean isEmail(String str) {
		if (isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}

	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * @param str
	 * @return boolean
	 */
	public boolean isMobile(String str) {
		if (isEmpty(str))
			return false;
		return str.matches("^(13|14|15|18)\\d{9}$");
	}

	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 判断字符串是否为非空(包含null与"")
	 * @param str
	 * @return
	 */
	public boolean isNotEmpty(String str) {
		if (str == null || "".equals(str))
			return false;
		return true;
	}

	/**
	 * 判断字符串是否为非空(包含null与"","    ")
	 * @param str
	 * @return
	 */
	public boolean isNotEmptyIgnoreBlank(String str) {
		if (str == null || "".equals(str) || "".equals(str.trim()))
			return false;
		return true;
	}

	/**
	 * 判断字符串是否为空(包含null与"")
	 * @param str
	 * @return
	 */
	public boolean isEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为空(包含null与"","    ")
	 * @param str
	 * @return
	 */
	public boolean isEmptyIgnoreBlank(String str) {
		if (str == null || "".equals(str) || "".equals(str.trim()))
			return true;
		return false;
	}
	
	public boolean isObjectNull(Object object) {
		if(object == null) {
			return true;
		}
		return false;
	}
	
	public boolean isNotObjectNull(Object object) {
		if(object != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 方法用途: 判断List是否为null<br>
	 * 实现步骤: <br>
	 * @param list
	 * @return
	 */
	public boolean isEmptyList(List<?> list){
		if(list==null ||list.size()==0){
			return true;
		}
		return false;
	}


	// 禁止实例化
	private ValidatorUtil() {
	}
}

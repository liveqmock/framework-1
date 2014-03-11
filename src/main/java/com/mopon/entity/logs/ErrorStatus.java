package com.mopon.entity.logs;

/**
 * 
 * <p>Description: 错误状态码</p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum ErrorStatus {
	
	/**
	 * 系统错误
	 */
	APPSYSTEM(10000),

	/**
	 *数据库错误
	 */
	DATABASE(10001),
	
	/**
	 *FTP错误
	 */
	FTP(10002),
	
	/**
	 *定时器错误
	 */
	TIMERTASK(10004),
	
	/**
	 *HTTP客户端库错误
	 */
	HTTPCLIENT(10005),
	
	/**
	 *XML错误
	 */
	XML(10007),
	
	/**
	 *json错误
	 */
	JSON(10008),
	
	/**
	 *buffer错误
	 */
	PROTO(10009),
	
	/**
	 *配置模块文件
	 */
	CONFIG(20000),
	
	CONFIG_SAVE(20001),
	
	CONFIG_UPDATE(20002),
	
	CONFIG_DELETE(20003),
	
	CONFIG_QUERY(20004),
	
	/**
	 *用户模块错误
	 */
	MEMBER(30000),
	
	MEMBER_NOT_FOUND(30001),
	
	MEMBER_PASSWORD_ERROR(30002),
	
	MEMBER_ALERADY_EXISTS(30003),
	
	MEMBER_NAME_IS_NULL(30004),
	
	MEMBER_PASSWORD_IS_NULL(30005),
	
	MEMBER_GET_DETAIL(30006),
	
	MEMBER_MODIFI(30007),
	
	MEMBER_DELETE(30008),
	
	MEMBER_QUERY(30009),
	
	/**
	 *jms模块错误
	 */
	JMS(40000),
	
	JMS_QUEUE_SEND(40001),
	
	JMS_TOPIC_SEND(40002),
	
	JMS_REVICE(40003),
	
	/**
	 *文件上传模块错误
	 */
	HTTP_FILE(50000),
	
	HTTP_FILE_OVER_SIZE(50001),
	
	HTTP_FILE_TYPE_ERROR(50002),
	
	/**
	 *分组模块错误
	 */
	GROUP(60000),
	
	GROUP_ADD(60001),
	
	GROUP_EDIT(60002),
	
	GROUP_REMOVE(60003),
	
	GROUP_ADD_ROLE(60004),
	
	GROUP_EDIT_ROLE(60005),
	
	GROUP_DETAIL(60006),
	
	GROUP_QUERY(60007),
	
	/**
	 *操作模块错误
	 */
	OPERATE(70000),
	
	OPERATE_ADD(70001),
	
	OPERATE_EDIT(70002),
	
	OPERATE_REMOVE(70003),
	
	OPERATE_ADD_GROUP(70004),
	
	OPERATE_EDIT_GROUP(70005),
	
	OPERATE_CANCEL_GROUP(70006),
	
	OPERATE_QUERY(70007),
	
	/**
	 *错误处理模块错误
	 */
	ERROR_MSG(80000),
	
	ERROR_MSG_ADD(80001),
	
	ERROR_MSG_DELETE(80002),
	
	ERROR_MSG_QUERY(80003),
	
	/**
	 *操作日志模块错误
	 */
	OP_MSG(90000),
	
	OP_MSG_ADD(90001),
	
	OP_MSG_DELETE(90002),
	
	OP_MSG_QUERY(90003),
	
	/**
	 *验证码模块错误
	 */
	VCODE_ERROR(10000),
	
	/**
	 *角色模块错误
	 */
	ROLE(11000),
	
	ROLE_ADD(11001),
	
	ROLE_EDIT(11002),
	
	ROLE_REMOVE(11003),
	
	ROLE_ADD_MENU(11004),
	
	ROLE_EDIT_MENU(11005),
	
	ROLE_DELETE_MENU(11006),

	ROLE_ADD_OPERATE(11007),
	
	ROLE_EDIT_OPERATE(11008),
	
	ROLE_DELETE_OPERATE(11009),
	
	ROLE_ADD_GROUP(11010),
	
	ROLE_EDIT_GROUP(11011),
	
	ROLE_DELETE_GROUP(11012),
	
	ROLE_QUERY(11013),
	
	ROLE_MENU_QUERY(11014),
	
	ROLE_OPERATE_QUERY(11015),
	
	ROLE_GROUP_QUERY(11016);
	
	private final int code;
	
	private ErrorStatus(int code) {
		this.code = code;
	}
	
	public int value() {
		return this.code;
	}
	
	public static ErrorStatus valueOf(int statusCode) {
		for (ErrorStatus status : values()) {
			if (status.code == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}

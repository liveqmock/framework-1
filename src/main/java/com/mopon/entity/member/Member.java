package com.mopon.entity.member;

import java.util.Date;



/**
 * 
 * <p>Description:用户表实体类 </p>
 * @date 2013年9月17日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class Member implements java.io.Serializable {
	
	private static final long serialVersionUID = 3784468023240040842L;
	
	/**
	 * 用户主键
	 */
	private Integer uid;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 邮件状态(1验证, 0未验证)
	 */
	private String emailStatus;
	
	/**
	 * 邮件地址
	 */
	private String email;
	
	/**
	 * 手机状态(1验证, 0未验证)
	 */
	private String mobileStatus;
	
	/**
	 * 注册IP
	 */
	private String regIP;
	
	/**
	 * 注册日期
	 */
	private Date regDate;
	
	/**
	 * 条件查询时使用
	 */
	private String  regDateStart;
	
	/**
	 * 条件查询时使用
	 */
	private String  regDateEnd;
	
	/**
	 * 注册来源
	 */
	private String regSrc;
	
	/**
	 * 用户状态(1有效，0无效，2停用)
	 */
	private String status;
	
	/**
	 * 用户类型(管理员、普通用户)
	 */
	private Integer type;
	
	/**
	 * 头像
	 */
	private Integer avatar;
	
	/**
	 * 头像地址
	 */
	private String avatarSrc;
	
	/**
	 * 积分
	 */
	private Integer integral;
	
	/**
	 * 等级
	 */
	private Integer level;
	
	/**
	 * 用户分组
	 */
	private Group group;
	
	/**
	 * 用户角色
	 */
	private Role role;
	
	/**
	 * 登陆系统次数
	 */
	private Integer loginNum;

	

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileStatus() {
		return mobileStatus;
	}

	public void setMobileStatus(String mobileStatus) {
		this.mobileStatus = mobileStatus;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	

	public String getRegSrc() {
		return regSrc;
	}

	public void setRegSrc(String regSrc) {
		this.regSrc = regSrc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAvatar() {
		return avatar;
	}

	public void setAvatar(Integer avatar) {
		this.avatar = avatar;
	}

	public String getAvatarSrc() {
		return avatarSrc;
	}

	public void setAvatarSrc(String avatarSrc) {
		this.avatarSrc = avatarSrc;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRegDateEnd() {
		return regDateEnd;
	}

	public void setRegDateEnd(String regDateEnd) {
		this.regDateEnd = regDateEnd;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegDateStart() {
		return regDateStart;
	}

	public void setRegDateStart(String regDateStart) {
		this.regDateStart = regDateStart;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	
}

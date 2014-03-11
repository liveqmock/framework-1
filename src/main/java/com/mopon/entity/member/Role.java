package com.mopon.entity.member;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 * @author liuguomin
 *
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4663194005207258842L;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 创建用户名
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createDate ;
	/**
	 * 角色描述
	 */
	private String roleDsc;
	
	/**
	 * 父类角色ID
	 */
	private Integer parentRoleId;
	
	/**
	 * 菜单
	 */
	private List<Menu> menus ;
	
	/**
	 * 权限操作
	 */
	private List<Operate> operates;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRoleDsc() {
		return roleDsc;
	}
	public void setRoleDsc(String roleDsc) {
		this.roleDsc = roleDsc;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Operate> getOperates() {
		return operates;
	}
	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getParentRoleId() {
		return parentRoleId;
	}
	public void setParentRoleId(Integer parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

}

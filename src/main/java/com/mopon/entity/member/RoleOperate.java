package com.mopon.entity.member;
/**
 * 角色权限实体类
 * @author liuguomin
 *
 */
public class RoleOperate {

	private Integer id;
	private Integer roleId;
	private Integer operateId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getOperateId() {
		return operateId;
	}
	public void setOperateId(Integer operateId) {
		this.operateId = operateId;
	}

	
}

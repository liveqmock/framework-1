package com.mopon.entity.member;


/**
 * 用户组-角色关系实体
 * @author liuguomin
 *
 */
public class GroupRole {

	private Integer id;
	private Integer roleId;
	private Integer groupId;
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	
}

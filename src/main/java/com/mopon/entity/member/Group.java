package com.mopon.entity.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: 分组管理实体</p>
 * @date 2013年9月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 766321023751077658L;

	/**
	 * 分组ID
	 */
	private Integer groupId;
	
	/**
	 * 分组名
	 */
	private String groupName;
	
	/**
	 * 创建时间
	 */
	private Date date;
	
	/**
	 * 创建用户
	 */
	private String username;
	
	/**
	 * 分组类型 0.本公司 1.渠道商 2.分销商 3.合作商 
	 */
	private Integer groupType;
	
	/**
	 * 分组描述
	 */	
	private String description;
	
	/**
	 * 分组包含的用户列表
	 */	
	private List<Member> members = new ArrayList<Member>();
	
	/**
	 * 分组包含的角色列表
	 */	
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * 父角色ID
	 */
	private Integer parentGroupId;
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Integer parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
}

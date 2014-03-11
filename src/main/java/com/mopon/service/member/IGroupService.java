package com.mopon.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mopon.entity.member.Group;
import com.mopon.entity.member.GroupRole;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.GroupException;

public interface IGroupService {

	public void addGroup(Group group, int[] roids) throws SQLException, GroupException;
	
	public void removeGroup(Group group) throws Exception;
	/**
	 * 
	 * @param group
	 * @param roleIds 要添加的角色
	 * @param delRoleIds 要删除的角色
	 * @throws SQLException
	 * @throws GroupException
	 */
	public void editGroup(Group group, int[] roleIds,int[] delRoleIds) throws Exception;
	
	public Group getGroupDetail(Group group) throws SQLException, GroupException;
	
	public Group queryGroupMemberForList(Group group) throws SQLException, GroupException;

	public PageBean<Group> getGroupForList(Group group, int pageNo, int pageSize, int sort,List<Group> supGroupList) throws SQLException, GroupException;
	
	public Group queryGroupForObject(Group group) throws SQLException, GroupException;
	
	public void addGroupRole(List<GroupRole> groupRole) throws SQLException, GroupException;
	
	public Group queryGroupRoleForList(Group group) throws SQLException, GroupException;
	
	public List<Group> queryGroupForList(Group group) throws SQLException, GroupException;
	
	/**
	 * 
	 * 方法用途: 返回分组包含的子角色列表<br>
	 * @param group
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public Group queryGroupSubordinateRole(Group group,Integer roleId) throws DataAccessException;
	
	/**
	 * 获取指定分组下所有子分组
	 * @param parentGroupId 
	 * @return
	 * @throws Exception
	 */
	public List<Group> queryGroupByParentGroupId(int parentGroupId)throws Exception;
	
	/**
	 * 组装分组
	 * @param list  要组装的所有分组
	 * @param parentGorupId 父分组ID
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getGroupMap(List<Group> list,int parentGroupId) throws Exception;

}

package com.mopon.dao.master.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mopon.entity.member.Group;
import com.mopon.entity.member.GroupRole;

/**
 * <p>Description: 用户分组管理</p>
 * @date 2013年9月9日
 * @author reaganjava
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface IGroupDao {
	
	/**
	 * 方法用途: 添加分组<br>
	 * 实现步骤: <br>
	 * @param entity 分组类实体
	 * @return 返回分组的ID
	 * @throws DatabaseException
	 */
	public int save(Group entity) throws SQLException;

	/**
	 * 方法用途: 删除分组<br>
	 * 实现步骤: <br>
	 * @param entity 分组类实体
	 * @return 无
	 * @throws DatabaseException
	 */
	public void remove(Group entity) throws SQLException;

	/**
	 * 方法用途: 更新分组<br>
	 * 实现步骤: <br>
	 * @param entity 分组类实体
	 * @return 无
	 * @throws DatabaseException
	 */
	public void update(Group entity) throws SQLException;
	
	/**
	 * 取消用户组
	 * @param groupId 组ID
	 */
	public void updateMemberGroup(int groupId);
	/**
	 * 方法用途: 根据ID返回分组<br>
	 * 实现步骤: <br>
	 * @param entity 分组类实体
	 * @return 返回ID对应的分组对象
	 * @throws DatabaseException
	 */
	public Group getGroupById(Group entity) throws SQLException;
	
	/**
	 * 方法用途: 根据ID返回分组<br>
	 * 实现步骤: <br>
	 * @param entity
	 * @return 返回的分组信息包含分组对应的用户列表和分组包含的角色列表
	 * @throws DatabaseException
	 */
	public Group getGroupDetailById(Group entity) throws SQLException;

	/**
	 * 方法用途: 根据条件查询分组<br>
	 * 实现步骤: <br>
	 * @param entity 分组实体
	 * @return 返回单个分组对象
	 * @throws DatabaseException
	 */
	public Group queryGroupForObject(Group entity) throws SQLException;
	
	/**
	 * 方法用途: 分组结果集统计<br>
	 * 实现步骤: <br>
	 * @param entity 分组实体
	 * @return 返回分组的结果数
	 * @throws DatabaseException
	 */
	public int queryGroupForCount(@Param("group")Group entity,@Param("supGroupList")List<Group> supGroupList) throws SQLException;
	
	/**
	 * 方法用途: 分页分组列表<br>
	 * 实现步骤: <br>
	 * @param entity 分组实体 start 开始位置 pageSize 页结果数
	 * @return 返回分组列表
	 * @throws DatabaseException
	 */
	public List<Group> queryGroupForPages(@Param("group") Group entity, @Param("start") int start, @Param("pageSize") int pageSize, @Param("sort") int sort,@Param("supGroupList")List<Group> supGroupList) throws SQLException;
	
	/**
	 * 方法用途: 返回分组包含用户列表<br>
	 * 实现步骤: <br>
	 * @param entity 分组对象
	 * @return 返回分组对象包含分组所对应的用户列表
	 * @throws DatabaseException
	 */
	public Group queryGroupMemberForList(Group entity) throws SQLException;
	
	/**
	 * 方法用途: 插入角色<br>
	 * 实现步骤: 分组与角色多对多在这里插入中间表数据实现多对多<br>
	 * @param groupRoles 分组角色列表
	 * @throws DatabaseException
	 */
	public void saveGroupRole(List<GroupRole> groupRoles) throws SQLException;
	
	/**
	 * 方法用途: 删除分组角色关联<br>
	 * 实现步骤: <br>
	 * @param groupId 分组ID
	 * @throws DatabaseException
	 */
	public void deleteGroupRoleByGroupId(int groupId) throws SQLException;
	/**
	 * 删除角色组
	 * @param groupId
	 * @param roleId
	 */
	public void deleteGroupRole(@Param("groupId")int groupId,@Param("roleId")int roleId);
	
	/**
	 * 方法用途: 返回分组包含角色列表<br>
	 * 实现步骤: <br>
	 * @param entity 分组对象
	 * @return 返回分组对象包含分组所对应的角色列表
	 * @throws DatabaseException
	 */
	public Group queryGroupRoleForList(Group group) throws SQLException;
	
	
	/**
	 * 
	 * 方法用途:返回分组包含的子角色列表 <br>
	 * @return
	 * @throws DataAccessException
	 */
	public Group queryGroupSubordinateRole(@Param("group")Group group,@Param("roleId") Integer roleId)throws DataAccessException;

	
	/**
	 * 方法用途: 返回分组列表<br>
	 * 实现步骤: <br>
	 * @param group 不设置任何值的时候查询全部
	 * @return 分组列表
	 * @throws SQLException
	 */
	public List<Group> queryGroupForList(Group group) throws SQLException;
}

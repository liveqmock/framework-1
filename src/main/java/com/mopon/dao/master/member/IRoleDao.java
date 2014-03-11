package com.mopon.dao.master.member;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import com.mopon.entity.member.Group;
import com.mopon.entity.member.GroupRole;

import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.entity.member.RoleMenu;
import com.mopon.entity.member.RoleOperate;

/**
 * 角色接口
 * @author liuguomin
 *
 */
@Repository
public interface IRoleDao {

	/**
	 * 保存角色
	 * @param entity
	 * @throws DataAccessException
	 */
	public void save(Role entity) ;
	
	/**
	 * 保存角色菜单
	 * @param roleMenu 角色菜单实体类
	 * @throws DataAccessException
	 */
	public void saveRoleMenu(RoleMenu roleMenu) ;
	/**
	 * 保存角色操作
	 * @param roleOperate 角色操作
	 * @throws DataAccessException
	 */
	public void saveRoleOperate(RoleOperate roleOperate) ;
	
	/**
	 * 保存角色组
	 * @param groupRole
	 */
	public void saveRoleGroup(GroupRole groupRole);
	/**
	 * 删除角色
	 * @param roleId  角色ID 
	 * @throws DataAccessException
	 */
	public void remove(int roleId) ;
	
	/**
	 * 删除角色菜单
	 * @param id 角色菜单ID
	 * @throws DataAccessException
	 */
	public void removeRoleMenuById(int id);
	/**
	 * 删除角色菜单
	 * @param roleId 角色ID
	 * @throws DataAccessException
	 */
	public void removeRoleMenuByRoleId(int roleId) ;
	
	/**
	 * 删除角色菜单
	 * @param roleId 角色ID
	 * @param menuId 菜单ID
	 */
	public void removeRoleMenu(@Param("roleId")int roleId,@Param("menuId")int menuId);
	/**
	 * 删除角色菜单
	 * @param menuId 菜单ID
	 * @throws DataAccessException
	 */
	public void removeRoleMenuByMenuId(int menuId);
	
	/**
	 * 删除角色操作
	 * @param id 角色操作ID
	 * @throws DataAccessException
	 */
	public void removeRoleOperateById(int id);
	/**
	 * 删除角色操作
	 * @param id 角色ID
	 * @throws DataAccessException
	 */
	public void removeRoleOperateByRoleId(int roleId);
	
	/**
	 * 删除角色操作
	 * @param roleId 角色ID
	 * @param operateId 操作ID
	 */
	public void removeRoleOperate(@Param("roleId")int roleId,@Param("operateId")int operateId);
	/**
	 * 删除角色操作
	 * @param operateId 操作ID
	 */
	public void removeRoleOperateByOperateId(int operateId) ;
	
	/**
	 * 删除角色组
	 * @param id 角色组ID
	 */
	public void removeRoleGroupById(int id);
	/**
	 * 删除角色组
	 * @param roleId 角色ID
	 * @throws DataAccessException
	 */
	public void removeRoleGroupByRoleId(int roleId);
	/**
	 * 删除角色组
	 * @param groupId 组ID
	 */
	public void removeRoleGroupByGroupId(int groupId);
	
	/**
	 * 更新角色
	 * @param entity
	 * @throws DataAccessException
	 */
	public void update(Role entity);
	
	/**
	 * 取消用户角色
	 * @param roleId 角色ID
	 */
	public void updateMemberRole(int roleId);
	/**
	 * 查询角色列表
	 * @param map 查询条件 <br>
	 *      key 为  查询字段 startPage pageSize roleId roleName createUser  <br>
	 *      value 为查询值
	 * @return
	 */
	//public List<Role> queryRoles(Map<String,Object> map);
	/**
	 * 查询角色列表
	 * @param role 查询条件
	 * @param startPage  起始记录数
	 * @param pageSize
	 * @return
	 */
	public List<Role> queryRoles(@Param("role")Role role,@Param("startPage")int startPage,@Param("pageSize")int pageSize);
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> queryAllRoles();
	/**
	 * 查询总条数
	 * @param role 查询条件
	 * @return
	 */
	public Integer queryCount(@Param("role")Role role);
	
	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public Role queryRoleByRoleID(int roleId);
	
	/**
	 * 根据角色名查询角色
	 * @param roleName
	 * @return
	 */
	public Role queryRoleByRoleName(String roleName);
	
	/**
	 * 查询指定角色所有子角色，不包含子角色的子角色
	 * @param parentRoleId
	 * @return
	 */
	public List<Role> queryRoleIdByParentRoleId(int parentRoleId);
	
	/**
	 * 根据角色ID查找菜单
	 * @param roleId
	 * @return
	 */
	public List<MenuItem> queryMenuByRoleID(int roleId);
	/**
	 * 根据角色ID查找操作
	 * @param roleId
	 * @return
	 */
	public List<Operate> queryOperateByRoleID(int roleId);
	/**
	 * 根据角色ID查找组
	 * @param roleId
	 * @return
	 */
	public List<Group> queryGroupByRoleID(int roleId);
	
	/**
	 * 根据组ID查询角色
	 * @param groupId
	 * @return
	 */
	public List<Role> queryRoleByGroupID(int groupId);
	
	/**
	 * 根据菜单ID查询角色
	 * @param menuId
	 * @return
	 */
	public List<Role> queryRoleByMenuID(int menuId);
	/**
	 * 
	 * 方法用途: 查询父角色<br>
	 * 实现步骤: <br>
	 * @return 返回所有父角色列表
	 */
	public List<Role> queryRoleByParent();
	
	/**
	 * 
	 * 方法用途: 查询子角色<br>
	 * 实现步骤: <br>
	 * @return 返回所有子角色列表
	 */
	public List<Role> queryRoleBySub();
}

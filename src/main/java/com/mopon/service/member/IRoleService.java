package com.mopon.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mopon.entity.member.Group;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.entity.member.RoleMenu;
import com.mopon.entity.member.RoleOperate;
import com.mopon.entity.sys.PageBean;

/**
 * 角色service
 * @author liuguomin
 *
 */
public interface IRoleService {
	/**
	 * 保存角色
	 * @param entity
	 * @param menuList 菜单ID list
	 * @param operateList 操作ID list
	 * @param groupId 组ID
	 * @throws SQLException
	 * @return int   1 成功   2 失败，存在角色
	 */
	public int save(Role entity,List<Integer> menuList,List<Integer> operateList,Integer groupId) throws Exception;
	/**
	 * 保存角色菜单
	 * @param roleMenu　角色菜单
	 * @throws SQLException
	 */
	public void saveRoleMenu(RoleMenu roleMenu) throws Exception;
	/**
	 * 保存角色权限
	 * @param roleOperate　角色操作
	 * @throws SQLException
	 */
	public void saveRoleOperate(RoleOperate roleOperate) throws Exception;
	
	/**
	 * 删除角色
	 * @param roleId 角色ID 
	 * @throws SQLException
	 */
	public void remove(int roleId) throws Exception;
	/**
	 * 批量删除角色
	 * @param roleIds 角色ID组
	 */
	public void remove(int[] roleIds) throws Exception;
	/**
	 * 删除角色菜单
	 * @param id 角色菜单ID
	 * @throws SQLException
	 */
	public void removeRoleMenuById(int id) throws Exception;
	/**
	 * 删除角色操作
	 * @param id 角色操作ID
	 * @throws SQLException
	 */
	public void removeRoleOperateById(int id) throws Exception;
	/**
	 * 删除角色组
	 * @param id 角色组ID
	 * @throws SQLException
	 */
	public void removeRoleGroupById(int id) throws Exception;
	/**
	 * 更新角色
	 * @param entity
	 * @param addMenuIdList 增加的菜单ID
	 * @param delMenuIdList 要删除的菜单ID
	 * @param addOperateIdList 增加的操作ID
	 * @param delOperateIdList 要删除的操作ID
	 * @throws Exception
	 */
	public void update(Role entity,List<Integer> addMenuIdList,List<Integer> delMenuIdList,List<Integer> addOperateIdList,List<Integer> delOperateIdList) throws Exception;
	
	/**
	 * 查询角色列表
	 * @param role 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean<Role> queryRoles(Role role,int pageNo,int pageSize)throws Exception;
	/**
	 * 查询角色总数
	 * @param role
	 * @return
	 */
	public int queryCount(Role role)throws Exception;
	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public Role queryRoleByRoleID(int roleId) throws Exception;
	
	/**
	 * 查询指定角色所有子角色
	 * @param parentRoleId
	 * @return
	 */
	public List<Role> queryRoleIdByParentRoleId(int parentRoleId)throws Exception;
	
	/**
	 * 查询指定角色的所有父角色
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> queryParentRoleByRoleId(int roleId) throws Exception;
	/**
	 * 根据角色ID查找菜单
	 * @param roleId
	 * @return
	 */
	public List<MenuItem> queryMenuByRoleID(int roleId)throws Exception;
	/**
	 * 根据角色ID查找操作
	 * @param roleId
	 * @return
	 */
	public List<Operate> queryOperateByRoleID(int roleId)throws Exception;
	
	/**
	 * 根据角色ID查找组
	 * @param roleId
	 * @return
	 */
	public List<Group> queryGroupByRoleID(int roleId)throws Exception;
	
	/**
	 * 根据组ID查询角色
	 * @param groupId
	 * @return
	 */
	public List<Role> queryRoleByGroupID(int groupId)throws Exception;
	
	/**
	 * 根据菜单ID查询角色
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<Role> queryRoleByMenuID(int menuId)throws Exception;
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> queryAllRoles()throws Exception;
	/**
	 * 将所有role 放入map中
	 * @param list role角色集合 <br>
	 * @param roleId 自己拥有的角色ID<br>
	 * @param myRoleGroup 具体分组拥有的角色 <br>
	 * @param myRoleMenu 具体菜单拥有的角色 <br>
	 * key id  角色ID(String)<br>
	 * key text  角色名称(String)<br>
	 * key childRole  子角色(List<Map>)<br>
	 */
	public Map<String,Object> getRoleMap(List<Role> list,int roleId,List<Role> myRoleGroup,List<Role> myRoleMenu)throws Exception;
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> getParentRole() throws Exception;
	
	
	public List<Role> getSubRole() throws Exception;

	/**
	 * 获取角色所拥有的菜单和操作数据，加载到Map中
	 * @param list 如果存在父类，则此菜单集合为父类角色的所有菜单集合 <br> 
	 * @param myMenu 角色拥有的菜单，用于判断复选框是否勾选
	 * @param myOperate 角色拥有的操作，用于判断复选框是否勾选
	 * @param flag 是否将不拥有的菜单也加载到Map<br> true 不加载 false 加载,一般用户查看菜单树时为true。
	 * @param operateList 加载父类所拥有的操作    
	 * @param loadsubMenu  是否加载节点菜单(拥有操作的那个菜单)  null 或者1 加载，0 不加载
	 * @param loadOperate 加载菜单时，是否加载菜单拥有的操作 : <br> null 或者 1  加载   ，0 不加载<br>
	 * 					备注：只有加载节点菜单 ，此参数才有效
	 * @param isShowChecked  是否显示复选框按钮   null 或者 1 显示   0 不显示
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMenuMap(List<MenuItem> list,List<MenuItem> myMenu,List<Operate> myOperate,boolean flag,List<Operate> operateList,boolean isShowChecked,boolean loadsubMenu,boolean loadOperate) throws Exception;
	/**
	 * 方法用途: 初始化用户角色对应的权利操作放入到缓存中<br>
	 * 实现步骤: <br>
	 * @param role 角色对象
	 * @throws Exception
	 */
	public void initMemberRolebyOperate(int uid, Role role) throws Exception;
}

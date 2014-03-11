package com.mopon.dao.master.member;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mopon.entity.member.MenuItem;

/**
 * 
 * Description:菜单管理Dao接口
 * 
 * @date 2013年9月18日
 * @author Jamie.Sun
 * @version 1.0 Company:Mopon Copyright:Copyright(c)2013
 */
@Repository
public interface IMenuItemDao {

	/**
	 * 新增
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	public void save(MenuItem entity)  throws DataAccessException;
	
	/**
	 * 批量新增菜单角色关联
	 * @param menuId
	 * @param roles
	 */
	public void saveMenuRole(MenuItem menuItem) throws DataAccessException;

	/**
	 * 根据ID删除菜单
	 * 
	 * @param menuId
	 * @throws SQLException
	 */
	public void deleteById(Integer menuId)  throws DataAccessException;

	/**
	 * 根据菜单Id和角色Id删除角色菜单关联
	 * @param menuId
	 */
	public void delMenuRole(@Param("menuId")  Integer menuId,@Param("roleId")  Integer roleId) throws DataAccessException;
	
	
	   /**
     * 根据菜单Id删除角色菜单关联
     * @param menuId
     */
    public void delMenuRoleByMenuId( Integer menuId) throws DataAccessException;
	
	
	
	/**
	 * 更新菜单
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	public void update(MenuItem entity)  throws DataAccessException;

	/**
	 * 根据ID查找菜单
	 * 
	 * @param menuId
	 * @return
	 * @throws SQLException
	 */
	public MenuItem queryById(Integer menuId)  throws DataAccessException;

	/**
	 * 查找分页数据总数
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer queryForCount(MenuItem menuItem)  throws DataAccessException;

	/**
	 * 查找分页数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<MenuItem> queryForPages(@Param("entity") MenuItem entity, @Param("start") int startPage, @Param("pageSize") int pageSize)
			 throws DataAccessException;

	   /**
     * 查找分页数据总数Admin
     * 
     * @return
     * @throws SQLException
     */
    public Integer queryForCountAdmin(MenuItem menuItem)  throws DataAccessException;

    /**
     * 查找分页数据Admin
     * 
     * @return
     * @throws SQLException
     */
    public List<MenuItem> queryForPagesAdmin(@Param("entity") MenuItem entity, @Param("start") int startPage, @Param("pageSize") int pageSize)
             throws DataAccessException;

	
	
	/**
	 * 根据菜单ID查询子菜单列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<MenuItem> queryMenuItemByMainMenuId(Integer menuId)
			 throws DataAccessException;
	
    /**
     * 根据用户ID查询菜单列表
     * 
     * @param userId
     * @return
     */
    public List<MenuItem> queryManMenuByUser(Integer userId)  throws DataAccessException;
    
    /**
     * 根据用户ID查询菜单列表(超级用户)
     * @return
     * @throws SQLException
     */
    public List<MenuItem> queryManMenuByAdmin(Integer userId)  throws DataAccessException;
   
    
    /**
     *  提供新增和修改界面下拉框的数据源，查询所有父菜单的id和name
     *  
     * @return 菜单列表
     */
    public List<MenuItem> queryMainMenuList() throws DataAccessException;
    
    /**
     * 查询全部
     * @return
     */
    public List<MenuItem> queryAllMenuItem() throws DataAccessException;
    
    /**
     * 查询子菜单（操作管理菜单下拉框）
     * @return
     * @throws DataAccessException
     */
    public List<MenuItem> querySubmenu(@Param("roleId")Integer roleId) throws DataAccessException;
    
    
}

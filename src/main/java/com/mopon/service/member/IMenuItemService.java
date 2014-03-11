package com.mopon.service.member;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mopon.entity.member.Member;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.MenuItemException;

/**
 * 
 * Description:菜单管理Service接口
 * 
 * @date 2013年9月18日
 * @author Jamie.Sun
 * @version 1.0 Company:Mopon Copyright:Copyright(c)2013
 */
public interface IMenuItemService {

    /**
     * 保存菜单信息
     * 
     * @param entity
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public void saveMenuItem(MenuItem entity) throws  MenuItemException,DataAccessException;

    /**
     * 根据ID删除菜单信息
     * 
     * @param menuId
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public void deleteMenuItemByIds(List<Integer> menuIds) throws  MenuItemException,DataAccessException;

    
    /**
     * 根据ID删除菜单信息
     * 
     * @param menuId
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public void deleteMenuItemById(Integer menuId) throws  MenuItemException,DataAccessException;
    
    /**
     * 更新菜单
     * 
     * @param entity
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public void updateMenuItem(MenuItem entity) throws  MenuItemException,DataAccessException;

    /**
     * 根据ID查询菜单信息
     * 
     * @return
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public MenuItem queryById(Integer menuId) throws  MenuItemException,DataAccessException;
    
    /**
     * 分页查询
     * 
     * @return
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public PageBean<MenuItem> queryForPages(MenuItem menuItem, int startPage, int pageSize) throws  MenuItemException,DataAccessException;

    /**
     * 权限记录数（用来判断是否存在）
     * @param menuItem
     * @return
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public Integer queryForCount(MenuItem menuItem) throws  MenuItemException,DataAccessException;
    
    /**
     * 根据菜单ID查询子菜单列表
     * 
     * @param menuId
     * @return
     * @throws DataAccessException
     */
    public List<MenuItem> queryMenuItemByMainMenuId(Integer menuId) throws  MenuItemException,DataAccessException;

    /**
     * 根据用户ID和主菜单ID获取对应的子菜单集合
     * 
     * @param userId   用户ID
     * @param mainMenuId 主菜单ID
     * @return List<MenuItem> 菜单树
     * @throws DataAccessException
     * @throws MenuItemException
     */
    public  List<MenuItem> queryManMenuByUser(Integer userId,Integer mainMenuId) throws  MenuItemException,DataAccessException;

    /**
     * 
     * @param roleId
     * @return 菜单列表（带对应操作）
     * @throws MenuItemException
     * @throws DataAccessException
     */
    public  List<MenuItem> queryMenuByMember(Member member) throws MenuItemException,DataAccessException,Exception;
    
    /**
     * 提供新增和修改界面下拉框的数据源，查询所有父菜单的id和name
     * 
     * @return 菜单列表
     */
    public List<MenuItem> queryMainMenuList() throws  MenuItemException,DataAccessException;

    /**
     * 查询可分配的操作
     * 
     * @return
     * @throws DataAccessException
     * @throws MenuItemException
     */
    public List<Operate> queryOperateByMenuId(int menuId) throws  MenuItemException,DataAccessException;
  
    /**
     * 根据菜单Id获取角色列表
     * @param menuId
     * @return
     * @throws DataAccessException
     * @throws MenuItemException
     */
    public List<Role> queryRoleByMenuId(int menuId) throws  MenuItemException,DataAccessException;

    /**
     * 查询所有菜单
     * 
     * @return
     * @throws DataAccessException
     * @throws MenuItemException
     */
    public List<MenuItem> queryAllMenuItem() throws  MenuItemException,DataAccessException;

    /**
     * 新增或修改菜单
     * @param menuItem
     * @param operateList
     * @param roleList
     * @throws DataAccessException
     * @throws MenuItemException
     */
    public void saveOrUpdateMenuItem(MenuItem menuItem,List<Integer> roldIds) throws  MenuItemException,DataAccessException;
        
    /**
     * 查询子菜单（操作管理菜单下拉框）
     * @return
     */
    public List<MenuItem> querySubmenu(Member member) throws  MenuItemException,DataAccessException;
    
}

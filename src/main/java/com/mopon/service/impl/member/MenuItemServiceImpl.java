package com.mopon.service.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.member.IMemberDao;
import com.mopon.dao.master.member.IMenuItemDao;
import com.mopon.dao.master.member.IOperateDao;
import com.mopon.dao.master.member.IRoleDao;
import com.mopon.dao.sys.ICachedDao;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.MenuItemException;
import com.mopon.exception.OperateException;
import com.mopon.service.member.IMenuItemService;

/**
 * 
 * Description:菜单管理Service接口实现类
 * 
 * @date 2013年9月18日
 * @author Jamie.Sun
 * @version 1.0 Company:Mopon Copyright:Copyright(c)2013
 */
@Service("menuItemServiceImpl")
public class MenuItemServiceImpl implements IMenuItemService {

    @Autowired
    private IMenuItemDao menuItemDaoImpl;
    @Autowired
    private IOperateDao operateDaoImpl;
    @Autowired
    private IRoleDao roleDaoImpl;
    @Autowired
    private IMemberDao memberDao;
    @Autowired
	protected ICachedDao cachedDao;

    /**
     * 保存菜单信息
     * 
     * @param entity
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    public void saveMenuItem(MenuItem entity)  throws  MenuItemException,DataAccessException {
        menuItemDaoImpl.save(entity);
    }

    /**
     * 根据ID集合删除菜单信息
     * 
     * @param menuId
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    @Transactional
    public void deleteMenuItemByIds(List<Integer> menuIds) throws  MenuItemException,DataAccessException {
        for(Integer menuId:menuIds){
            deleteMenuItemById(menuId);
        }       
    }
    
    /**
     * 根据ID删除菜单信息
     * 
     * @param menuId
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    @Transactional
    public void deleteMenuItemById(Integer menuId) throws  MenuItemException,DataAccessException {

        //查询对应的子菜单
        List<MenuItem> menuItems= menuItemDaoImpl.queryMenuItemByMainMenuId(menuId);
        
        //判断是否需要删除子菜单
       
        for(MenuItem item:menuItems){
        	if(item.getMenuItems()!=null&&item.getMenuItems().size()>0){
    		 List<Integer> list =new ArrayList<Integer>();
        		for(MenuItem menu:item.getMenuItems()){
        			list.add(menu.getMenuId());
        		}
        		//递归删除
        		deleteMenuItemByIds(list);
            }
    		//递归删除
    		deleteMenuItemById(item.getMenuId());
        }

        //根据菜单Id删除操作与角色的关联
        operateDaoImpl.removeRoleOperateByMenuId(menuId);
        
        // 根据菜单ID删除角色菜单关联表
        roleDaoImpl.removeRoleMenuByMenuId(menuId);
      
        // 根据菜单ID删除操作信息
        operateDaoImpl.removeByMenuID(menuId);
        
        // 根据菜单ID删除菜单信息
        menuItemDaoImpl.deleteById(menuId);
       
    }

    /**
     * 更新菜单
     * 
     * @param entity
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    public void updateMenuItem(MenuItem entity) throws  MenuItemException,DataAccessException {
        menuItemDaoImpl.update(entity);
    }

    /**
     * 根据ID查询菜单信息
     * 
     * @return
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    public MenuItem queryById(Integer menuId) throws  MenuItemException,DataAccessException {
        return menuItemDaoImpl.queryById(menuId);
    }

    public List<MenuItem> queryAllMenuItem() throws  MenuItemException,DataAccessException {
        return menuItemDaoImpl.queryAllMenuItem();
    }

    /**
     * 分页查询全部菜单数据
     * 
     * @return
     * @throws MenuItemException
     * @throws SQLException
     */
    @Override
    public PageBean<MenuItem> queryForPages(MenuItem menuItem, int startPage, int pageSize) throws  MenuItemException,DataAccessException {

        // 创建分页对象
        PageBean<MenuItem> pageBean = new PageBean<MenuItem>();

        // 设置当前页
        pageBean.setCurrentPage(startPage);
        if (startPage > 0) {
            startPage = startPage - 1;
        }

        // 设置开始位置
        int start = startPage * pageSize;
        int count=0;
        List<MenuItem> groupList =null;

        if(menuItem.getRoleId()==null){
            // 得到结果总数
            count = menuItemDaoImpl.queryForCountAdmin(menuItem);

            // 返回列表
           groupList = menuItemDaoImpl.queryForPagesAdmin(menuItem, start, pageSize);
            
        }else{
            // 得到结果总数
            count = menuItemDaoImpl.queryForCount(menuItem);

            // 返回列表
           groupList = menuItemDaoImpl.queryForPages(menuItem, start, pageSize);
        }
       

        // 放入分页容器
        pageBean.setDataList(groupList);

        // 设置页大小
        pageBean.setPageSize(pageSize);

        //总记录数
        pageBean.setRecordCount(count);

        
        return pageBean;

    }
    
    public Integer queryForCount(MenuItem menuItem){
    	return menuItemDaoImpl.queryForCountAdmin(menuItem);
    }

    /**
     * 根据菜单ID查询子菜单列表
     * 
     * @param menuId
     * @return
     * @throws SQLException
     */
    @Override
    public List<MenuItem> queryMenuItemByMainMenuId(Integer menuId) throws  MenuItemException,DataAccessException {
        return menuItemDaoImpl.queryMenuItemByMainMenuId(menuId);
    }

    @Override
    public  List<MenuItem> queryManMenuByUser(Integer userId,Integer mainMenuId) throws  MenuItemException,DataAccessException {
        List<MenuItem> menuItems=null;
        Member member = new Member();
        member.setUid(userId);
        
        //是否是超级用户
        if(memberDao.queryMemberById(member).getUserName().equals("admin")){
            menuItems=menuItemDaoImpl.queryManMenuByAdmin(userId);
        }else{
            menuItems = menuItemDaoImpl.queryManMenuByUser(userId);
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> queryMainMenuList() throws  MenuItemException,DataAccessException {
        return menuItemDaoImpl.queryMainMenuList();
    }

    @Override
    public List<Operate> queryOperateByMenuId(int menuId) throws  MenuItemException,DataAccessException {
        return operateDaoImpl.queryByMenuId(menuId);
    }
    
    @Override
    public List<Role> queryRoleByMenuId(int menuId) throws  MenuItemException,DataAccessException {
        return roleDaoImpl.queryRoleByMenuID(menuId);
    }

    @Override
    @Transactional
    public void saveOrUpdateMenuItem(MenuItem menuItem,List<Integer> roleIds) throws  MenuItemException,DataAccessException {
    	
        //如果是新增
        if(menuItem.getMenuId()==null){

            //保存菜单信息,并将主键ID存到菜单对象
            menuItemDaoImpl.save(menuItem);
            
            //添加角色菜单关联
            if(menuItem.getRoleId()!=null){
                if(roleIds!=null){
            		for(Integer roleId:roleIds){
            			
            			menuItem.setRoleId(roleId);
            			//添加菜单角色关联(父角色)
                        menuItemDaoImpl.saveMenuRole(menuItem);
            		}
            	}
            }
            
        }else{
            
            //更新菜单信息
            menuItemDaoImpl.update(menuItem);
              
        }
        
    }
    
    /**
     * 筛选工具
     * 方法用途：筛选出菜单的显示
     * @param oldItem
     * @param newItem
     */
    public  void getShow(List<MenuItem> oldItem,List<MenuItem> newItem){
        for(MenuItem menu:oldItem){
            if(menu.getMenuQuickMenu()==1||menu.getMenuStartMenu()==1){
                newItem.add(menu);
                continue;
            }else{
                if(menu.getMenuItems()==null){
                    
                }
                getShow(menu.getMenuItems(),newItem);
            }
        }
    }

    /**
     * 根据用户获取菜单
     */
    @Override
    public  List<MenuItem> queryMenuByMember(Member member) throws MenuItemException,DataAccessException,Exception {
        List<MenuItem> menuItems=null;
        List<Operate> operates=null;
        List<Operate> ops=null;
        Set<String> opurl= new HashSet<String>();
        
        Integer uid=member.getUid();
        
        //是否是超级用户
        if(memberDao.queryMemberById(member).getUserName().equals("admin")){
            
            //获取菜单
            menuItems=menuItemDaoImpl.queryManMenuByAdmin(member.getUid());
            
            //获取操作(超级用户)
            operates=operateDaoImpl.getOperateByRoleId(null);        
            
        }else{
            
            //获取菜单
            menuItems = menuItemDaoImpl.queryManMenuByUser(member.getUid());
            
            //获取操作
            operates=operateDaoImpl.getOperateByRoleId(member.getRole().getRoleId());
        }
        
        //容错处理
        for(Operate o : operates){
        	if(o.getOpPriority()==null){
        		o.setOpPriority(0);
        	}
        	
        	if(o.getOpMenuId()==null){
        		o.setOpMenuId(0);
        	}
        }
       
        //操作排序
        sortedOperate(operates);
        
        //组装
        for(MenuItem menuItem:menuItems){
            ops= new ArrayList<>();
            
            //容错处理
            if(menuItem.getMenuPriority()==null){
            	menuItem.setMenuPriority(0);
        	}
            for(Operate operate:operates){
            	
            	if(operate.getOpUrl()!=null){
						String[] str=operate.getOpUrl().split(",");
						for(String url:str){
							
							//将操作url放入缓存，用于过滤权限
			            	opurl.add(url);
						}
            	}
            	
                if((operate.getOpMenuId()).equals(menuItem.getMenuId())){
                    ops.add(operate);
                }
            }
            if(menuItem.getMenuUrl()!=null){
            	String[] str=menuItem.getMenuUrl().split(",");
				for(String url:str){
					
					//将菜单url放入缓存，用于过滤权限
	            	opurl.add(url);
				}
        	}
            menuItem.setOperates(ops);
        }
        
        //将操作url放入缓存
        cachedDao.set(uid+"ACTIONURL", opurl);
        
        MenuItem newmenu = new MenuItem();

        // 设置主菜单Id,1为所有菜单的主菜单，即没有父菜单Id
        newmenu.setMenuId(1);

        // 递归获得子菜单，去掉重复菜单并排序
        getChildMenu(menuItems, newmenu);
        
        
        List<MenuItem> newItem= new ArrayList<MenuItem>();
        
        //筛选出桌面图标
        getShow(newmenu.getMenuItems(),newItem);
        return newItem;
    }

    /**
     * 组合菜单并排序
     * 
     * @param menuItems
     */
    private void getChildMenu(List<MenuItem> oldMenuItems, MenuItem newmenu) {
        if (oldMenuItems == null || newmenu == null) {
            return;
        }

        Integer parentMenuId = newmenu.getMenuId();      

        // 得到相关的子菜单 获得parentMenu下的子节点
        List<MenuItem> childList = new ArrayList<MenuItem>();
        for (int i = 0; i < oldMenuItems.size(); i++) {
            MenuItem menu = oldMenuItems.get(i);
            Integer menuId = menu.getMainMenuId();
            if(menuId==null)
            	continue;
            if (menuId.equals(parentMenuId)) {
                childList.add(menu);
            }
        }
        List<MenuItem> sortchildList = null;
        sortchildList = childList;
        sortedMenu(sortchildList);
        newmenu.setMenuItems(sortchildList);

        // 递归得到所有子节点的子节点
        for (Iterator<MenuItem> it = childList.iterator(); it.hasNext();) {
            MenuItem menuitem = it.next();
            getChildMenu(oldMenuItems, menuitem);
        }
    }

    /**
     * 根据菜单的优先级排序
     * 
     * @param menuItems
     */
    public void sortedMenu(List<MenuItem> menuItems) {
        Collections.sort(menuItems, new Comparator<MenuItem>() {
            public int compare(MenuItem a, MenuItem b) {
                int one = a.getMenuPriority();
                int two = b.getMenuPriority();
                return one - two;
            }
        });
    }
    
    /**
     * 根据菜单的优先级排序
     * 
     * @param menuItems
     */
    public void sortedOperate(List<Operate> operates) {
        Collections.sort(operates, new Comparator<Operate>() {
            public int compare(Operate a, Operate b) {
                int one = a.getOpPriority();
                int two = b.getOpPriority();
                return one - two;
            }
        });
    }
    
    public List<MenuItem> querySubmenu(Member member) throws MenuItemException,DataAccessException  {
        return menuItemDaoImpl.querySubmenu(member.getRole().getRoleId());
    }
}

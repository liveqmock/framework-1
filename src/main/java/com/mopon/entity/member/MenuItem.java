package com.mopon.entity.member;

import java.util.Date;
import java.util.List;

/**
 * 
 * Description:菜单实体类
 * 
 * @date 2013年9月18日
 * @author Jamie.Sun
 * @version 1.0 Company:Mopon Copyright:Copyright(c)2013
 */
public class MenuItem {

	/**
	 * 菜单ID
	 */
	private Integer menuId;
	
	/**
	 * 菜单名
	 */
	private String menuName;
	
	/**
     * 菜单别名
     */
	private String menuWName;
	
	/**
	 * 请求地址
	 */
	private String menuAction;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 菜单大图标
	 */
	private String menuIconcls;

	/**
     * 菜单小图标
     */
    private String menuSmallcls;
    
	/**
	 * 菜单级别
	 */
	private Integer menuPriority;
	
	/**
	 * 菜单状态
	 */
	private Integer menuStatus;
	
	/**
	 * 开始菜单
	 */
	private Integer menuStartMenu;
	
	/**
     * 快捷菜单
     */
    private Integer menuQuickMenu;

	/**
	 * 上级菜单ID
	 */
	private Integer mainMenuId;
	
	/**
	 * 父菜单
	 */
	private MenuItem menuItem;
	
	/**
	 * 子菜单
	 * @return
	 */
	private List<MenuItem> menuItems;
	
	/**
     * 菜单URL
     */
    private String menuUrl;
	
	/**
     * 角色
     */
    private List<Role> roles;
    
    /**
     * 角色ID
     */
    private Integer roleId;
    
    /**
     * 操作
     */
    private List<Operate> operates;
    
    /**
     * 开始日期（创建时间的筛选条件）
     */
    private String startDate;
    
    /**
     * 结束日期（创建时间的筛选条件）
     */
    private String endDate;
    
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getMenuPriority() {
		return menuPriority;
	}

	public void setMenuPriority(Integer menuPriority) {
		this.menuPriority = menuPriority;
	}

	public Integer getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(Integer menuStatus) {
		this.menuStatus = menuStatus;
	}

	public Integer getMainMenuId() {
		return mainMenuId;
	}

	public void setMainMenuId(Integer mainMenuId) {
		this.mainMenuId = mainMenuId;
	}

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Operate> getOperates() {
        return operates;
    }

    public void setOperates(List<Operate> operates) {
        this.operates = operates;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public String getMenuIconcls() {
        return menuIconcls;
    }

    public void setMenuIconcls(String menuIconcls) {
        this.menuIconcls = menuIconcls;
    }

    public String getMenuSmallcls() {
        return menuSmallcls;
    }

    public void setMenuSmallcls(String menuSmallcls) {
        this.menuSmallcls = menuSmallcls;
    }

    public Integer getMenuStartMenu() {
        return menuStartMenu;
    }

    public void setMenuStartMenu(Integer menuStartMenu) {
        this.menuStartMenu = menuStartMenu;
    }

    public Integer getMenuQuickMenu() {
        return menuQuickMenu;
    }

    public void setMenuQuickMenu(Integer menuQuickMenu) {
        this.menuQuickMenu = menuQuickMenu;
    }

    public String getMenuWName() {
        return menuWName;
    }

    public void setMenuWName(String menuWName) {
        this.menuWName = menuWName;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
}

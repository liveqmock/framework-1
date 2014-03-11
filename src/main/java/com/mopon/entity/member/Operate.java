package com.mopon.entity.member;

import java.util.Date;

/**
 * 
 * <p>Title: 权限操作实体类</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2013</p>
 * <p>Company:mopon</p>
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 */
public class Operate {

    /**
     * 操作ID
     */
	private Integer opId;
	
	/**
	 * 操作名
	 */
	private String opName;

	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
    private Date createDate;
	
    /**
     * 操作请求
     */
	private String opAction;
	
	/**
     * 操作是否显示按钮
     */
	private int opIsBtn;
	
	/**
     * 按钮ID
     */
	private String btnId;
	
	/**
	 * 操作描述
	 */
	private String opDesc;
	
	/**
	 * 对应菜单ID
	 */
	private Integer opMenuId;

   /**
     * 对应菜单
     */
	private MenuItem menuItem;
	
	/**
	 * 角色Id
	 */
	private Integer roleId;
	
	/**
     * 按钮的请求url
     */
	private String opUrl;
	
	/**
	 * 按钮顺序
	 */
	private Integer opPriority;

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    /**
	 * 开始日期（创建时间的筛选条件）
	 */
	private String startDate;
	
	/**
	 * 结束日期（创建时间的筛选条件）
	 */
	private String endDate;
	
    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
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

    public String getOpAction() {
        return opAction;
    }

    public void setOpAction(String opAction) {
        this.opAction = opAction;
    }

    public int getOpIsBtn() {
		return opIsBtn;
	}

	public void setOpIsBtn(int opIsBtn) {
		this.opIsBtn = opIsBtn;
	}

	public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
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

    public Integer getOpMenuId() {
        return opMenuId;
    }

    public void setOpMenuId(Integer opMenuId) {
        this.opMenuId = opMenuId;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

	public String getOpUrl() {
		return opUrl;
	}

	public void setOpUrl(String opUrl) {
		this.opUrl = opUrl;
	}

	public Integer getOpPriority() {
		return opPriority;
	}

	public void setOpPriority(Integer opPriority) {
		this.opPriority = opPriority;
	}
}
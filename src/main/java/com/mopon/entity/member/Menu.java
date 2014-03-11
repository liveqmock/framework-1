package com.mopon.entity.member;

/**
 * 
 * <p>Title: 菜单角色表实体类</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2013</p>
 * <p>Company:mopon</p>
 * @date 2013年8月22日
 * @author tongbiao
 * @version 1.0
 */
public class Menu {
	
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 菜单编号
	 */
	private int menuId;
	
	/**
	 * 菜单名称
	 */
	private String text;
	
	
	/**
	 * 菜单图标
	 */
	private String iconCls;
	
	/**
	 * 菜单地址
	 */
	private String url;
	

	/**
	 * 树形菜单是否有下级节点
	 */
	private String leaf;




	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}



	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}

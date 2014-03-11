package com.mopon.service.member;

import java.util.List;

import com.mopon.entity.member.Menu;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.MenuItemException;

public interface IMenuService {
	
	public List<Menu> queryMenu() throws MenuItemException, DatabaseException;
	
	public List<Menu> queryByMenu(Menu entity) throws MenuItemException, DatabaseException;
}

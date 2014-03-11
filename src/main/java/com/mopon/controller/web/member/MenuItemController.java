package com.mopon.controller.web.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.dao.master.member.IMemberDao;
import com.mopon.dao.sys.ICachedDao;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultList;
import com.mopon.entity.sys.ResultObject;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.MenuItemException;
import com.mopon.service.member.IMemberService;
import com.mopon.service.member.IMenuItemService;
import com.mopon.service.member.IRoleService;
import com.mopon.util.Session;

/**
 * 菜单功能模块控制层
 * 
 * @author Jamie.Sun
 * @date 2013-09-18
 * @version 1.0 Company:Mopon Copyright:Copyright(c)2013
 * 
 */
@Controller
@RequestMapping(value = "/menuItem")
public class MenuItemController extends Component {
	
	@Autowired
	protected ICachedDao cachedDao;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMenuItemService menuItemServiceImpl;

	@Autowired
	private IRoleService roleServiceImpl;

	/**
	 * 删除菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public ModelAndView deleteMenuItem(ModelAndView mav, Integer[] menuIds) {
		ResultObject<MenuItem> results = new ResultObject<MenuItem>();
		try {
			List<Integer> menus= new ArrayList<>();
			for(Integer in:menuIds){
				menus.add(in);
			}
			menuItemServiceImpl.deleteMenuItemByIds(menus);
			results.setMessage("菜单删除成功！");
			results.setSuccess(true);

		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new MenuItemException(errorMsg);
			results.setSuccess(false);
			results.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", results);
		}

		return mav;
	}

	@RequestMapping(value = "saveOrUpdateMenuItem", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateMenuItem(ModelAndView mav,
			MenuItem menuItem, HttpServletRequest request) {

		ResultObject<MenuItem> results = new ResultObject<MenuItem>();
		
		//视图别名为空
		if(menuItem.getMenuWName()==""){
			menuItem.setMenuWName(null);
		}

		try {

			 //获取用户名
            Member member=memberService.findCachedMember(request);
			
			String mainMenuId = request.getParameter("mainMenuId");

			if(mainMenuId==""||menuItem.getMainMenuId().equals(0)){
				results.setMessage("请选择父级！");
				results.setSuccess(false);
				return mav;
			}
			
			if(member.getRole()!=null){
				menuItem.setRoleId(member.getRole().getRoleId());
			}
			
			// 如果是新增
			if (menuItem.getMenuId() == null) {
				MenuItem menu = new MenuItem();
				menu.setMenuWName(menuItem.getMenuWName());
				Integer count=menuItemServiceImpl.queryForCount(menu);
				if(count>0){
					results.setMessage("视图别名已存在！");
					results.setSuccess(false);
					return mav;
				}
				
				//设置菜单初始参数
				menuItem.setCreateUser(member.getUserName());
				menuItem.setCreateDate(new Date());
				menuItem.setMenuStatus(1);
			}

			if(menuItem.getRoleId()==null){
				menuItemServiceImpl.saveOrUpdateMenuItem(menuItem,null);
			}else{
				List<Integer> roleIds=roleServiceImpl.queryParentRoleByRoleId(menuItem.getRoleId());
				if(roleIds.size()==0){
					roleIds.add(member.getRole().getRoleId());
				}
				menuItemServiceImpl.saveOrUpdateMenuItem(menuItem,roleIds);
			}
			
			results.setMessage("操作成功！");
			results.setSuccess(true);

		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new MenuItemException(errorMsg);
			results.setSuccess(false);
			results.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", results);
		}

		return mav;
	}

	public static java.util.Date parseDate(String date, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	/**
	 * 获取菜单列表
	 * 
	 * @param response
	 * @param request
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "findMenuItemList")
	public ModelAndView findMenuItemList(ModelAndView mav, MenuItem menuItem,
			Integer page, Integer limit, HttpServletRequest request) {
		PageBean<MenuItem> pageBean = null;
		try {
			Member member = memberService.findCachedMember(request);
			Integer roleId = null;
			if (!member.getUserName().equals("admin")) {
				// 角色Id
				roleId = member.getRole().getRoleId();
				menuItem.setRoleId(roleId);
			}
			// 日期判断处理
			if(StringUtils.isNotEmpty(menuItem.getStartDate())){
				menuItem.setStartDate(menuItem.getStartDate() +" 00:00:00");
			}
			if(StringUtils.isNotEmpty(menuItem.getEndDate())){
				menuItem.setEndDate(menuItem.getEndDate() +" 23:59:59");
			}
			pageBean = menuItemServiceImpl.queryForPages(menuItem, page, limit);
			pageBean.setSuccess(true);
			pageBean.setMessage("操作成功");
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			pageBean.setSuccess(false);
			pageBean.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new MenuItemException(errorMsg);
			pageBean.setSuccess(false);
			pageBean.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", pageBean);
		}
		return mav;
	}

	/**
	 * 获取菜单状态
	 * 
	 * @param mav
	 * @param menuItem
	 * @return
	 */
	@RequestMapping(value = "findMenuStatus")
	public ModelAndView findMenuStatus(ModelAndView mav, MenuItem menuItem) {
		ResultList<Map<String, Object>> resultObject = new ResultList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			Map<String, Object> map = new HashMap<String, Object>();

			map = new HashMap<>();
			map.put("menuId", "1");
			map.put("menuName", "启用");
			list.add(map);

			map = new HashMap<>();
			map.put("menuId", "0");
			map.put("menuName", "禁用");
			list.add(map);

			resultObject.setResultList(list);
			resultObject.setSuccess(true);
			resultObject.setMessage("操作成功");
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			resultObject.setSuccess(false);
			resultObject.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new MenuItemException(errorMsg);
			resultObject.setSuccess(false);
			resultObject.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", resultObject);
		}
		return mav;
	}

	/**
	 * 根据菜单ID
	 * 
	 * @param mav
	 * @param menuId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showMenu")
	public ModelAndView showMenu(ModelAndView mav, HttpServletRequest request) {

		ResultList<Object> result = new ResultList<Object>();
		List<Object> data = new ArrayList<Object>();
		try {
			Member member=memberService.findCachedMember(request);
			// 获取视图信息
			List<MenuItem> menuItems = menuItemServiceImpl.queryMenuByMember(member);

			for (MenuItem menuItem : menuItems) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag", menuItem.getMenuId().toString());
				map.put("name", menuItem.getMenuName());
				map.put("iconCls", menuItem.getMenuIconcls());
				map.put("widgetName", menuItem.getMenuWName());
				map.put("controller", menuItem.getMenuAction());
				map.put("isAddToQuickStar",menuItem.getMenuStartMenu() == 1 ? "yes" : "no");
				map.put("isAddToStarMenu",menuItem.getMenuQuickMenu() == 1 ? "yes" : "no");
				map.put("smallIconCls", menuItem.getMenuSmallcls());
				map.put("submenu", menuItem.getMenuItems());
				map.put("operates", menuItem.getOperates());
				data.add(map);
			}
			result.setResultList(data);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			result.setSuccess(false);
			result.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new MenuItemException(errorMsg);
			result.setSuccess(false);
			result.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", result);
		}
		return mav;
	}
}

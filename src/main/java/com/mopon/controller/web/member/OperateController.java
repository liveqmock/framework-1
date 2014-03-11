package com.mopon.controller.web.member;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.member.Operate;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultList;
import com.mopon.entity.sys.ResultObject;
import com.mopon.exception.DatabaseException;
import com.mopon.exception.OperateException;
import com.mopon.service.member.IMemberService;
import com.mopon.service.member.IMenuItemService;
import com.mopon.service.member.IOperateService;
import com.mopon.service.member.IRoleService;
import com.mopon.util.Session;

/**
 * 
 * <p>
 * Description: 权限操作控制层
 * </p>
 * 
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 *          <p>
 *          Company:Mopon
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2013
 *          </p>
 */
@Controller
@RequestMapping(value = "/operate")
public class OperateController extends Component {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMenuItemService menuItemServiceImpl;

	@Autowired
	private IOperateService operateService;

	@Autowired
	private IRoleService roleServiceImpl;

	@RequestMapping(value = "edit/{opId}", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView mav, @PathVariable int opId) {
		ResultObject<Operate> results = new ResultObject<Operate>();
		try {
			// 如果是新增
			if (opId == -1) {
				Operate operate = new Operate();
				operate.setOpId(opId);
				mav.addObject("operate", operate);
			} else {
				mav.addObject("operate", operateService.findOperateById(opId));
			}
			results.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			results.setSuccess(false);
			results.setMessage("操作失败！");
		} finally {
			mav.addObject("jsonData", results);
		}
		return mav;
	}

	// 操作下拉框（待完善）
	@RequestMapping(value = "querySubMenu", method = RequestMethod.GET)
	public ModelAndView querySubMenu(ModelAndView mav,HttpServletRequest request) {
		ResultList<MenuItem> results = new ResultList<MenuItem>();
		try {
			// 获取用户名
			Member member = memberService.findCachedMember(request);
			results.setResultList(menuItemServiceImpl.querySubmenu(member));
			results.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			results.setSuccess(false);
			results.setMessage("操作失败！");
		} finally {
			mav.addObject("jsonData", results);
		}
		return mav;
	}

	@RequestMapping(value = "saveOrUpdateOperate", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateOperate(ModelAndView mav, Operate operate,
			HttpServletRequest request) {
		ResultObject<Operate> results = new ResultObject<Operate>();

		String opMenuId = request.getParameter("opMenuId");
		try {
			
			if (opMenuId == null) {
				results.setSuccess(false);
				results.setMessage("请选择对应的菜单");
				return mav;
			}
			//如果是新增判断操作是否已存在
			if(operate.getOpId()==null){
				Operate op = new Operate();
				op.setOpAction(operate.getOpAction());
				op.setOpMenuId(Integer.parseInt(opMenuId));
				if(operateService.getOperate(op)>0){
					results.setSuccess(false);
					results.setMessage("该菜单已存在对应的请求已存在");
					return mav;
				}
			}
			

			// 获取用户名
			Member member = memberService.findCachedMember(request);

			if (operate.getOpId() == null) {
				operate.setCreateUser(member.getUserName());
				if (member.getRole() != null) {
					operate.setRoleId(member.getRole().getRoleId());
				}
				operate.setCreateDate(new Date());
			}
			if (operate.getRoleId() == null) {
				operateService.saveOrUpdateOperate(operate, null);
			} else {
				List<Integer> roleIds = roleServiceImpl.queryParentRoleByRoleId(operate.getRoleId());
				if (roleIds.size() == 0) {
					roleIds.add(member.getRole().getRoleId());
				}
				operateService.saveOrUpdateOperate(operate, roleIds);
			}
			results.setSuccess(true);
			results.setMessage("操作成功");
		
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("数据库异常！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			results.setSuccess(false);
			results.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", results);
		}
		return mav;
	}

	@RequestMapping(value = "/findOperate")
	public ModelAndView findOperate(ModelAndView mav, Operate operate,
			Integer page, Integer limit, HttpServletRequest request) {
		PageBean<Operate> operates = new PageBean<Operate>();
		try {
			Member member = memberService.findCachedMember(request);
			Integer roleId = null;
			if (!member.getUserName().equals("admin")) {
				// 角色Id
				roleId = member.getRole().getRoleId();
				operate.setRoleId(roleId);
			}
			// 日期判断处理
			if(StringUtils.isNotEmpty(operate.getStartDate())){
				operate.setStartDate(operate.getStartDate() +" 00:00:00");
			}
			if(StringUtils.isNotEmpty(operate.getEndDate())){
				operate.setEndDate(operate.getEndDate() +" 23:59:59");
			}
			// 获取列表
			operates = operateService.findOperate(operate, page, limit);
			operates.setMessage("操作成功");
			operates.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			operates.setSuccess(false);
			operates.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			operates.setSuccess(false);
			operates.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", operates);
		}
		return mav;
	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	public ModelAndView deleteOperate(ModelAndView mav, int[] opIds) {
		ResultObject<Operate> results = new ResultObject<Operate>();
		try {
			operateService.removeOperate(opIds);
			results.setMessage("删除成功！");
			results.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			results.setSuccess(false);
			results.setMessage("数据库异常，删除失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			results.setSuccess(false);
			results.setMessage("系统异常，删除失败！");
		} finally {
			mav.addObject("jsonData", results);
		}
		return mav;
	}

	@RequestMapping(value = "getOperate/{menuId}")
	public ModelAndView getOperate(ModelAndView mav,
			@PathVariable Integer menuId, HttpServletRequest request) {
		ResultList<Operate> resultList = new ResultList<Operate>();

		String uid = cookieManager.readCookie("uid", request);
		Session sesion = sessionManager
				.getSession(request.getSession().getId());
		Member member = (Member) sesion.getAttribute(uid);
		String userName = member.getUserName();

		try {
			if (userName.equals("admin")) {
				resultList
						.setResultList(operateService.getAdminOperate(menuId));
			} else {
				resultList.setResultList(operateService.getOperate(member
						.getRole().getRoleId(), menuId));
			}

			resultList.setMessage("操作成功！");
			resultList.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			resultList.setSuccess(false);
			resultList.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.GROUP_EDIT.value(),
					e.getMessage(), ErrorLevel.ERROR.value(),
					ErrorType.APPLICATION_ERROR.value());
			new OperateException(errorMsg);
			resultList.setSuccess(false);
			resultList.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", resultList);
		}
		return mav;
	}
}

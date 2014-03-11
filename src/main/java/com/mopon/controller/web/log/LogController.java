package com.mopon.controller.web.log;

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
import com.mopon.entity.logs.Logging;
import com.mopon.entity.logs.OperateMsg;
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
import com.mopon.service.sys.ILoggingService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.util.Session;

/**
 * 
 * <p>
 * Description: log控制层
 * </p>
 * 
 * @date 2013年9月22日
 * @author 王丽松
 * @version 1.0
 * <p>
 * Company:Mopon
 * </p>
 * <p>
 * Copyright:Copyright(c)2013
 * </p>
 */
@Controller
@RequestMapping(value = "/log")
public class LogController extends Component {

	@Autowired
	private IOperateMsgService operateMsgService;
	
	@Autowired
	private ILoggingService loggingService;

	@RequestMapping(value = "/findOperateLog")
	public ModelAndView findOperateLog(ModelAndView mav, OperateMsg operateMsg,
			Integer page, Integer limit, HttpServletRequest request) {
		PageBean<OperateMsg> operateMsgs = new PageBean<OperateMsg>();
		try {
//			Member member = memberService.findCachedMember(request);
//			Integer roleId = null;
//			if (!member.getUserName().equals("admin")) {
//				// 角色Id
//				roleId = member.getRole().getRoleId();
//				operate.setRoleId(roleId);
//			}
			// 日期判断处理
			if(StringUtils.isNotEmpty(operateMsg.getStartDate())){
				operateMsg.setStartDate(operateMsg.getStartDate() +" 00:00:00");
			}
			if(StringUtils.isNotEmpty(operateMsg.getEndDate())){
				operateMsg.setEndDate(operateMsg.getEndDate() +" 23:59:59");
			}
			// 获取列表
			operateMsgs = operateMsgService.queryOperateMsgForList(operateMsg, page, limit);
			operateMsgs.setMessage("操作成功");
			operateMsgs.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			operateMsgs.setSuccess(false);
			operateMsgs.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			operateMsgs.setSuccess(false);
			operateMsgs.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", operateMsgs);
		}
		return mav;
	}
	
	@RequestMapping(value = "/findLogging")
	public ModelAndView findLogging(ModelAndView mav, Logging logging,
			Integer page, Integer limit, HttpServletRequest request) {
		PageBean<Logging> loggings = new PageBean<Logging>();
		try {
//			Member member = memberService.findCachedMember(request);
//			Integer roleId = null;
//			if (!member.getUserName().equals("admin")) {
//				// 角色Id
//				roleId = member.getRole().getRoleId();
//				operate.setRoleId(roleId);
//			}
			// 日期判断处理
			if(StringUtils.isNotEmpty(logging.getStartDate())){
				logging.setStartDate(logging.getStartDate() +" 00:00:00");
			}
			if(StringUtils.isNotEmpty(logging.getEndDate())){
				logging.setEndDate(logging.getEndDate() +" 23:59:59");
			}
			// 获取列表
			loggings = loggingService.queryLoggingForList(logging, page, limit);
			loggings.setMessage("操作成功");
			loggings.setSuccess(true);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			loggings.setSuccess(false);
			loggings.setMessage("数据库异常，操作失败！");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			loggings.setSuccess(false);
			loggings.setMessage("系统异常，操作失败！");
		} finally {
			mav.addObject("jsonData", loggings);
		}
		return mav;
	}
}

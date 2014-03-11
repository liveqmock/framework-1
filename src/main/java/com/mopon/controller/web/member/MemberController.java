package com.mopon.controller.web.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.dao.sys.ICachedDao;
import com.mopon.entity.logs.ErrorLevel;
import com.mopon.entity.logs.ErrorMsg;
import com.mopon.entity.logs.ErrorStatus;
import com.mopon.entity.logs.ErrorType;
import com.mopon.entity.logs.Logging;
import com.mopon.entity.logs.OPType;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.member.Group;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.Role;
import com.mopon.entity.sys.PageBean;
import com.mopon.entity.sys.ResultList;
import com.mopon.entity.sys.ResultObject;
import com.mopon.exception.DatabaseException;
import com.mopon.service.member.IGroupService;
import com.mopon.service.member.IMemberService;
import com.mopon.service.member.IRoleService;
import com.mopon.service.sys.ILoggingService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.util.Base64Utils;

/**
 * 
 * <p>Description: 用户管理控制层</p>
 * @date 2013年9月22日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends Component {
	
	private static final String ADMIN="admin";

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IGroupService groupService;
	

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private  ICachedDao cachedDao;
	
	@Autowired
	private ILoggingService loggingService;
	
	@Autowired
	private IOperateMsgService operateMsgService;
	
	/**
	 * 
	 * 方法用途: 用户修改、新增页面的跳转及修改页面数据的获取<br>
	 * @param mav
	 * @param member
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(ModelAndView mav, Member member, String path) {
		try {
			if ("edit".equals(path)) {// 用户修改页面
				Member entity = this.memberService.queryMemberById(member);
				mav.addObject("member", entity);
				mav.setViewName("member/edit");//用户修改页面
			} else {
				mav.setViewName("member/add");// 用户新增页面
			}
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
		}

		return mav;
	}

	/**
	 * 
	 * 方法用途: 查找用户<br>
	 * @param mav
	 * @param pageNum 第几页
	 * @return
	 */
	@RequestMapping(value = "findUser")
	public ModelAndView findUser(HttpServletRequest request,ModelAndView mav, Member member, Integer page,Integer limit) {
		PageBean<Member> pageBean = new PageBean<Member>();
		pageBean.setDateline(System.currentTimeMillis());
		try {
			List<Role> roles = null;
			if(!ADMIN.equals(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)))){
				Integer roleId = this.memberService.findCachedMember(request).getRole().getRoleId();
				roles = roleService.queryRoleIdByParentRoleId(roleId);
				Role role = new Role();
				role.setRoleId(roleId);//分管理登录系统时根据角色进行数据过滤,角色包含自己.
				roles.add(role);
			}
			// 日期判断处理
			if(StringUtils.isNotEmpty(member.getRegDateStart())){
				member.setRegDateStart(member.getRegDateStart() +" 00:00:00");
			}
			if(StringUtils.isNotEmpty(member.getRegDateEnd())){
				member.setRegDateEnd(member.getRegDateEnd() +" 23:59:59");
			}
			pageBean = memberService.queryMemberForList(page, limit, member,roles);
			pageBean.setSuccess(true);
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			pageBean.setSuccess(false);
			pageBean.setMessage("查找用户数据失败！");
			
		}
		mav.addObject("jsonData", pageBean);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 更新用户信息<br>
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "update")
	public ModelAndView updateMember(Member member, ModelAndView mav, HttpServletRequest request) {
		member.setAvatar(1);
		member.setAvatarSrc("1");
		//member.setRegIP(request.getRemoteAddr());
		member.setRegDate(new Date());
		member.setRegSrc("0");// 注册来源
		ResultObject<Member> resultObject = new ResultObject<Member>();
		try {
			this.memberService.modifiMember(member);
			resultObject.setSuccess(true);
			resultObject.setMessage("更新成功");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			resultObject.setSuccess(false);
			resultObject.setMessage("更新用户数据失败！");
		}
		
		//修改用户日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.UPDATE.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("修改用户");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		mav.addObject("jsonData", resultObject);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 保存用户信息<br>
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "save")
	public ModelAndView saveMember(ModelAndView mav, Member member, HttpServletRequest request) {
		member.setAvatar(1);
		member.setAvatarSrc("1");
		member.setEmailStatus("0");// 邮箱状态
		member.setLevel(1);
		member.setMobileStatus("0");// 手机状态
		member.setPassword(md5.getMD5ofStr(member.getPassword()));
		member.setRegIP(request.getRemoteAddr());
		member.setRegDate(new Date());
		member.setRegSrc("0");
		member.setStatus("0");
		ResultObject<Member> resultObject = new ResultObject<Member>();
		try {
			Member verifyMember = new Member();
			verifyMember.setUserName(member.getUserName());
			// 判断用户名是否已存在
			Member entity = this.memberService.verifyMember(verifyMember);
			if (entity != null) {
				resultObject.setSuccess(false);
				resultObject.setMessage("该用户已注册【" + member.getUserName() + "】");
				mav.addObject("jsonData", resultObject);
				return mav;
			}

			// 判断邮箱是否已注册
			verifyMember = new Member();
			verifyMember.setEmail(member.getEmail());
			entity = this.memberService.verifyMember(verifyMember);
			if (entity != null) {
				resultObject.setSuccess(false);
				resultObject.setMessage("该邮箱已注册【" + member.getEmail() + "】");
				mav.addObject("jsonData", resultObject);
				return mav;
			}
			// 手机号是否已注册
			
			this.memberService.addMember(member);// 添加用户
			resultObject.setSuccess(true);
			resultObject.setMessage("新增成功");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			resultObject.setSuccess(false);
			resultObject.setMessage("添加用户数据失败！");
		}
		
		//新增用户日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.ADD.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("新增用户");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		mav.addObject("jsonData", resultObject);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 删除用户信息<br>
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "delete")
	public ModelAndView deleteMember(ModelAndView mav, String[] listCheckbox,HttpServletRequest request) {
		ResultObject<Member> resultObject = new ResultObject<Member>();
		try {
			this.memberService.removeMember(listCheckbox);
			resultObject.setSuccess(true);
			mav.addObject("jsonData", resultObject);// 提示页面框架删除成功
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			ErrorMsg errorMsg = new ErrorMsg(ErrorStatus.DATABASE.value(),e.getMessage(),ErrorLevel.ERROR.value(),ErrorType.APPLICATION_ERROR.value());
			new DatabaseException(errorMsg);
			resultObject.setSuccess(false);
			resultObject.setMessage("删除用户数据失败！");
			mav.addObject("jsonData", resultObject);
		}
		//删除用户日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.DELETE.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("删除用户");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 查询所有分组信息<br>
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "findGroup")
	public ModelAndView findGroup(ModelAndView mav,HttpServletRequest request) {
		ResultList<Group> resultList = new ResultList<Group>();
		try {
			String userName = Base64Utils.decodeToString(cookieManager.readCookie("userName", request));
			List<Group> groups = null;
			if(ADMIN.equals(userName)){
				groups = this.groupService.queryGroupForList(null);
			}else {
				Member member = this.memberService.findCachedMember(request);
				//groups = this.groupService.queryGroupForList(member.getGroup());
				groups.add(member.getGroup());
				groups.addAll(groupService.queryGroupByParentGroupId(member.getGroup().getGroupId()));
			}
			
			resultList.setSuccess(true);
			resultList.setMessage("成功");
			resultList.setResultList(groups);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			resultList.setSuccess(false);
			resultList.setMessage("获取分组异常");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			resultList.setSuccess(false);
			resultList.setMessage("获取分组异常");
		}
		mav.addObject("jsonData", resultList);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 根据分组ID查询该分组下所有角色<br>
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "findRoleByGroupId")
	public ModelAndView findRole(ModelAndView mav, Group group,HttpServletRequest request) {
		ResultList<Role> resultList = new ResultList<Role>();
		try {
			Group entity = null;
			String userName = Base64Utils.decodeToString(cookieManager.readCookie("userName", request));
			if(ADMIN.equals(userName)){
				entity = this.groupService.queryGroupRoleForList(group);
			}else {
				Member member = this.memberService.findCachedMember(request);
				Integer roleId = member.getRole().getRoleId();
				if(!"".equals(roleId)){
					entity = this.groupService.queryGroupSubordinateRole(group, roleId);
				}
			}
			
			List<Role> roles = new ArrayList<Role>();
			if(entity != null){
				roles = entity.getRoles();
			}
			
			resultList.setSuccess(true);
			resultList.setResultList(roles);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			resultList.setSuccess(false);
			resultList.setMessage("获取分组下角色异常");
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			resultList.setSuccess(false);
			resultList.setMessage("获取分组下角色异常");
		}
		mav.addObject("jsonData", resultList);
		return mav;
	}

	/**
	 * 
	 * 方法用途: 跳转到修改密码页面<br>
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "changePwd")
	public ModelAndView changePwd(ModelAndView mav) {
		mav.setViewName("member/changePwd");
		return mav;
	}

	/**
	 * 
	 * 方法用途: 修改用户密码<br>
	 * @param mav
	 * @param member
	 * @param request
	 * @param oldPwd 旧密码
	 * @return
	 */
	@RequestMapping(value = "doChangePwd")
	public ModelAndView doChangePwd(ModelAndView mav, Member member, HttpServletRequest request,String oldPwd) {
		ResultObject<Member> resultObject = new ResultObject<Member>();
		try {
			String uid = cookieManager.readCookie("uid", request);
			member.setUid(Integer.parseInt(uid));
			member.setPassword(md5.getMD5ofStr(member.getPassword()));// MD5加密
			Member entity = this.memberService.queryMemberById(member);
			if(!entity.getPassword().equals(md5.getMD5ofStr(oldPwd))){
				resultObject.setSuccess(false);
				resultObject.setMessage("旧密码不正确！");
				mav.addObject("jsonData", resultObject);
				return mav;
			}
			this.memberService.modifiMember(member);
			resultObject.setSuccess(true);
			mav.addObject("jsonData", resultObject);
		} catch (DataAccessException e) {
			loggerUtil.error(e.getMessage());
			resultObject.setSuccess(false);
			mav.addObject("jsonData", resultObject);
		} catch (Exception e) {
			loggerUtil.error(e.getMessage());
			resultObject.setSuccess(false);
			resultObject.setMessage("系统异常");
			mav.addObject("jsonData", resultObject);
		}
		return mav;
	}
}

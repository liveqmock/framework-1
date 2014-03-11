package com.mopon.controller.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mopon.component.Component;
import com.mopon.dao.sys.ICachedDao;
import com.mopon.entity.logs.Logging;
import com.mopon.entity.logs.OPType;
import com.mopon.entity.logs.OperateMsg;
import com.mopon.entity.member.Member;
import com.mopon.entity.member.MenuItem;
import com.mopon.entity.sys.ResultObject;
import com.mopon.exception.MemberException;
import com.mopon.service.member.IMemberService;
import com.mopon.service.member.IMenuItemService;
import com.mopon.service.member.IRoleService;
import com.mopon.service.sys.ILoggingService;
import com.mopon.service.sys.IOperateMsgService;
import com.mopon.util.Base64Utils;
import com.mopon.util.CookieManager;
import com.mopon.util.Session;

import javax.servlet.http.HttpSession;

/**
 * 系统后台管理
 * <p>Description: </p>
 * @date 2013年9月25日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Controller
@RequestMapping("/system")
public class SystemController extends Component{
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	protected ICachedDao cachedDao;
	
	@Autowired 
	private IRoleService roleService;
	
	@Autowired
	private IMenuItemService menuItemService;
	
	@Autowired
	private ILoggingService loggingService;
	
	@Autowired
	private IOperateMsgService operateMsgService;
	
	@RequestMapping(value = "index.html", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mv ,HttpServletRequest request,HttpServletResponse response){
		return center(mv,request,response);
	}
	
	/**
	 * 
	 * 方法用途: 用户登录<br>
	 * @param mav
	 * @param member
	 * @return
	 */
	@RequestMapping(value="login")
	public ModelAndView login(ModelAndView mav,Member member,HttpServletRequest request,HttpServletResponse response){
		Member entity = null;
		ResultObject<Member> ro = new ResultObject<Member>();
		try{
			//校验用户信息
			member.setPassword(md5.getMD5ofStr(member.getPassword()));//MD5加密
			entity = this.memberService.queryMemberByName(member);//根据用户名及密码查询用户信息
			if(entity==null){
				ro.setSuccess(false);
				ro.setMessage("用户名或密码错误！");
				ro.setResult(null);
				mav.addObject("jsonData", ro);
			}else{
				//设置Cookie及Session
				String sessionId = request.getSession().getId();
				ResultObject<MenuItem> resultObject = new ResultObject<MenuItem>();
				cookieManager.writeCookie("userName",Base64Utils.encode(entity.getUserName().getBytes()),CookieManager.DAY,response);//设置用户名,有效期为一天
				cookieManager.writeCookie("uid",entity.getUid().toString(),CookieManager.DAY,response);//设置用户ID,有效期为一天
				cookieManager.writeCookie("sessionId",sessionId,CookieManager.DAY,response);//设置用户sessionId,有效期为一天
				//创建SESSION
				Session session  = this.sessionManager.getSession(sessionId);
				//向SESSION设置值
				session.setAttribute(request.getContextPath()+entity.getUid().toString(),entity);//将用户信息保存至Session
				request.getSession().setAttribute("userId", entity.getUid().toString());//处理更改系统时间不能获取到UID
				//session.setAttribute("userId", entity.getUid().toString());//处理更改系统时间不能获取到UID
				cachedDao.set(request.getContextPath()+entity.getUid().toString(),entity);//将用户信息保存至缓存
				resultObject.setSuccess(true);
				mav.addObject("jsonData", resultObject);//返回登录是否成功信息
				
				//更新用户登录次数
				Member loginNumMember = new Member();
				loginNumMember.setUid(entity.getUid());
				if(entity.getLoginNum()==null || "".equals(entity.getLoginNum())){
					entity.setLoginNum(0);
				}
				loginNumMember.setLoginNum(entity.getLoginNum()+1);
				this.memberService.updateLoginNum(loginNumMember);
				
				//添加登录日志
				Logging logging=new Logging();
				logging.setLogUid((long)entity.getUid());
				logging.setLogName(entity.getUserName());
				logging.setLogDate(new Date());
				logging.setLogMobile(entity.getMobile());
				logging.setLogClientType(9999);
				logging.setLogSiteId(new Integer(9999));
				logging.setLogSiteName("开发平台");
				logging.setLogIp(request.getRemoteAddr());
				logging.setLogInfo("登录系统");
				loggingService.addLogging(logging);
				
				
			}
		}catch(DataAccessException e){
			loggerUtil.error(e.getMessage());
			ro.setSuccess(false);
			ro.setMessage("系统异常");
			mav.addObject("jsonData", ro);
		}catch(Exception e){
			loggerUtil.error(e.getMessage());
			ro.setSuccess(false);
			ro.setMessage("系统异常");
			mav.addObject("jsonData", ro);
		}
		
		return mav;
	}
	
	/**
	 * 
	 * 方法用途: 用户退出,清空用户Session和Cookie<br>
	 * @param mav
	 * @param member
	 * @param request
	 * @return
	 */
	@RequestMapping(value="loginOut.html")
	public ModelAndView loginOut(ModelAndView mav,HttpServletRequest request,HttpServletResponse response){
		Session session  = this.sessionManager.getSession(request.getSession().getId());
		session.removeSession(cookieManager.readCookie("uid", request));//清除session
		session.removeSession("userId");
		cookieManager.removeCookie("uid", request, response);
		mav.setViewName("signIn");
		
		//添加登录日志
		Logging logging=new Logging();
		logging.setLogDate(new Date());
		logging.setLogClientType(9999);
		logging.setLogIp(request.getRemoteAddr());
		logging.setLogInfo("登出系统");
		loggingService.addLogging(logging);
		return mav;
	}
	
	@RequestMapping(value="center")
	public ModelAndView center(ModelAndView mav,HttpServletRequest request,HttpServletResponse response){
		String uid = cookieManager.readCookie("uid", request);
		//Session session  = this.sessionManager.getSession(request.getSession().getId());
		HttpSession session = request.getSession();
		if(uid ==  null || "".equals(uid)){
			Object userId = session.getAttribute("userId");
			if(userId == null ||  "".equals(userId)){
				mav.setViewName("signIn");
			}else{
				Member entity = new Member();
				try {
					entity = (Member)cachedDao.get(request.getContextPath()+session.getAttribute("userId"));
				} catch (Exception e) {
					loggerUtil.error(e.getMessage());
				}
				cookieManager.writeCookie("userName",Base64Utils.encode(entity.getUserName().getBytes()),CookieManager.DAY,response);//设置用户名,有效期为一天
				cookieManager.writeCookie("uid",entity.getUid().toString(),CookieManager.DAY,response);//设置用户ID,有效期为一天
				mav.setViewName("index");
			}
		}else{
			mav.setViewName("index");
		}
		return mav;
	}
	
	/**
	 * 检查用户是否是第一次登陆
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="validateLoginFirst")
	public ModelAndView validateLoginFirst(ModelAndView mav,Member member){
		ResultObject<Member> ro = new ResultObject<Member>();
		//检查用户是否是第一次登陆
		member.setPassword(md5.getMD5ofStr(member.getPassword()));//MD5加密
		Member entity = this.memberService.queryMemberByName(member);
		if(entity!=null){
			if(null==entity.getLoginNum() || "".equals(entity.getLoginNum()) || entity.getLoginNum()==0){//第一次登陆
				ro.setSuccess(true);//第一次登陆
			}
		}else{
			ro.setSuccess(false);
		}
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	
	/**
	 * 用户如果是第一次登陆修改密码
	 * @param mav
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param confirmPwd 确认密码
	 * @return
	 */
	@RequestMapping(value="doLoginFirst")
	public ModelAndView doLoginFirst(ModelAndView mav,String oldPwd,String newPwd,String userName){
		ResultObject<Object> ro = new ResultObject<Object>();
		//修改密码
		Member member = new Member();
		member.setUserName(userName);
		//验证旧密码是否正确
		Member dbMember = this.memberService.queryMemberByName(userName);

		if(!dbMember.getPassword().equals(md5.getMD5ofStr(oldPwd))){
			ro.setSuccess(false);
			ro.setMessage("旧密码不正确！");
			mav.addObject("jsonData", ro);
			return mav;
		}
		if(dbMember.getPassword().equals(md5.getMD5ofStr(newPwd))){//修改前密码和修改后密码相同
			ro.setSuccess(false);
			ro.setMessage("修改前密码和修改后密码不能相同！");
			mav.addObject("jsonData", ro);
			return mav;
		}
		member.setPassword(md5.getMD5ofStr(newPwd));
		try {
			this.memberService.changePwd(member);
		} catch (DataAccessException | MemberException e) {
			loggerUtil.error(e.getMessage());
		}
		ro.setSuccess(true);//修改密码成功
		mav.addObject("jsonData", ro);
		return mav;
	}
	
	/**
	 * 如果用户是第一次登陆跳转到修改密码页面
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="changePwdFirstLogin")
	public ModelAndView changePwdFirstLogin(ModelAndView mav){
		mav.setViewName("changePwdFirstLogin");
		return mav;
	}
	
	
	/**
	 * 后台跳转到修改密码页面
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="doChangePwd")
	public ModelAndView doChangePwd(ModelAndView mav){
		mav.setViewName("changePwd");
		return mav;
	}
	
	/**
	 * 后台修改用户密码
	 * @param mav
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changePwd")
	public ModelAndView changePwd(ModelAndView mav,String password,String oldPwd,HttpServletRequest request){
		
		ResultObject<Object> ro = new ResultObject<Object>();
		Member member = new Member();
		member.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		//验证旧密码是否正确
		Member dbMember = this.memberService.queryMemberById(member);
		member.setUserName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		if(!dbMember.getPassword().equals(md5.getMD5ofStr(oldPwd))){
			ro.setSuccess(false);
			ro.setMessage("旧密码不正确！");
			mav.addObject("jsonData", ro);
			return mav;
		}
		member.setPassword(md5.getMD5ofStr(password));
		try {
			this.memberService.changePwd(member);
		} catch (DataAccessException | MemberException e) {
			loggerUtil.error(e.getMessage());
		}
		ro.setMessage("密码修改成功");
		ro.setSuccess(true);
		
		//修改密码日志
		List<OperateMsg> opList = new ArrayList<OperateMsg>();
		OperateMsg om = new OperateMsg();
		om.setOpType(OPType.UPDATE.getOpType());
		om.setUid(Integer.valueOf(cookieManager.readCookie("uid", request)));
		om.setName(Base64Utils.decodeToString(cookieManager.readCookie("userName", request)));
		om.setSiteId(1);
		om.setSiteName("系统");
		om.setDateline(new Date());
		om.setMessage("修改密码");
		opList.add(om);
		operateMsgService.addBatchOperateMsg(opList);
				
		mav.addObject("jsonData", ro);
		return mav;
	}
	
}
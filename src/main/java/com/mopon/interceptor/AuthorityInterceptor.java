package com.mopon.interceptor;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mopon.dao.sys.ICachedDao;
import com.mopon.entity.member.Member;
import com.mopon.entity.sys.Config;
import com.mopon.service.member.IMemberService;
import com.mopon.service.sys.IConfigService;
import com.mopon.util.CookieManager;
import com.mopon.util.LoggerUtil;
import com.mopon.util.SessionManager;

/**
 * <p>Description: 权限控制拦截器</p>
 * @date 2013年9月18日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private IMemberService memberService;
    
	
	/**
	 * COOKIE
	 */
	@Autowired
	private CookieManager cookieManager;
	
	/**
	 * SESSION管理
	 */
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 缓存
	 */
	@Autowired
	private ICachedDao cachedDao;
	
	/**
	 * 日志
	 */
	@Autowired
	private LoggerUtil loggerUtil;
	
	/**
	 * 配置管理
	 */
	@Autowired
	private IConfigService configService;
	
	/**
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isSuccess = super.preHandle(request, response, handler); 
		String uri = request.getRequestURI().toUpperCase();
		boolean isFilter = true;
		loggerUtil.info("=============== URI:" + request.getRequestURI() + "===============");
		//重系统配置字典中获取不需要拦截的URI地址
		Config config = configService.getConfig("NO_RIGHT_PATHS");
		if(config != null) {
			String[] noRightPaths = config.getValue().toUpperCase().split(",");
			for(String noRightPath : noRightPaths) {
				System.out.println(noRightPath);
				if(uri.indexOf("CENTER")!= -1){
					break;
				}
				if(uri.indexOf(noRightPath) != -1) {
					isFilter = false;
					break;
				}
			}
			//判断请求是否是不需要拦截的地址组
			if(isFilter) {
				//得到用户的UID
				Member member=memberService.findCachedMember(request);
				
				if(member != null) {
					if(!member.getUserName().equals("admin")) {
						Set<String> actionList = (Set<String>) cachedDao.get(member.getUid() + "ACTIONURL");
						if(actionList != null) {
							boolean flag=false;
							for(String action : actionList) {
								if(uri.indexOf(action.toUpperCase()) != -1) {
									flag=true;
									break;
								}
								String[] str=action.split(",");
								for(String url:str){
									if(uri.indexOf(url.toUpperCase()) != -1) {
										flag=true;
										break;
									}
								}
								if(flag)break;
							}
							if(uri.indexOf("CENTER")!= -1){
								flag=true;
							}
							if(flag!=true){
								writeMessage("您没有权限访问该功能", response);
								isSuccess = false;
							}
						} else {
							writeMessage("您没有权限访问该功能", response);
							isSuccess = false;
						}
					}
				} else {
					loggerUtil.info("=============== member uid is null ===============");
					
					writeMessage("服务器正在维护，请重新登录！", response);
					isSuccess = false;
//					response.sendRedirect("/framework/system/index.html");
//					isSuccess = false;
				}
			}
		} else {
			writeMessage("系统的‘NO_RIGHT_PATHS’配置项为空，请联系管理员进行处理", response);
			isSuccess = false;
		}
		return isSuccess;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		loggerUtil.info("=============== postHandle ===============");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		loggerUtil.info("=============== afterCompletion ===============");
	}
	
	private void writeMessage(String msg, HttpServletResponse response) throws IOException {
		String tip = "{\"success\":false,\"dateline\":1381976285099,\"message\":\"" + msg + "\"}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(tip);
		response.getWriter().flush();
	}

}

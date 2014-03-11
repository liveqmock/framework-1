package com.mopon.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * <p>Description:请求过滤器</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MyHiddenHttpMethodFilter extends HiddenHttpMethodFilter {

	/**
	 * 请求函数类型的参数
	 */
	private String methodParam = DEFAULT_METHOD_PARAM;

	/**
	 * 方法用途: 设置请求类型<br>
	 * 实现步骤: 根据_method参数来设置函数类型<br>
	 * @param methodParam
	 */
	public void setMethodParam(String methodParam) {
		this.methodParam = methodParam;
	}

	/**
	 * 方法用途: 进行请求函数转换<br>
	 * 实现步骤: <br>
	 * @param request 请求类
	 * @param response 响应类
	 * @param filterChain 过滤类
	 * @throws ServletException 服务器端异常
	 * @throws IOException IO异常
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String paramValue = request.getParameter(methodParam);
		String _method = request.getMethod();
		if (StringUtils.hasLength(paramValue)) {
			String method = paramValue.toUpperCase(Locale.ENGLISH);
			boolean b = ("POST".equals(_method) && "PUT"
					.equalsIgnoreCase(method))
					|| ("GET".equals(_method) && "DELETE"
							.equalsIgnoreCase(method));
			if (b) {
				HttpServletRequest wrapper = new HttpMethodRequestWrapper(
						request, method);
				filterChain.doFilter(wrapper, response);
			} else {

			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private static class HttpMethodRequestWrapper extends
			HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request,
				String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

}
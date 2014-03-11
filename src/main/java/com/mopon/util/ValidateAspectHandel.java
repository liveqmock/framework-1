package com.mopon.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>Description: 验证注解处理类</p>
 * @date 2013年10月17日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component
@Aspect
public class ValidateAspectHandel {

	/**
	 * 日志记录
	 */
	@Autowired
	protected LoggerUtil loggerUtil;

	

	/**
	 * 使用AOP对使用了ValidateGroup的方法进行代理校验
	 * @throws Throwable 
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@Around("@annotation(com.mopon.util.ValidateGroup)")
	public Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean flag = false;
		String message = "";// 提示消息
		ValidateGroup an = null;
		Object[] args = null;
		Method method = null;
		Object target = null;
		String methodName = null;
		try {
			methodName = joinPoint.getSignature().getName();
			target = joinPoint.getTarget();
			method = getMethodByClassAndName(target.getClass(), methodName); // 得到拦截的方法
			args = joinPoint.getArgs(); // 方法的参数
			an = (ValidateGroup) getAnnotationByMethod(method, ValidateGroup.class);
			flag = Boolean.valueOf(validateFiled(an.fileds(), args).get("flag").toString());
			message = validateFiled(an.fileds(), args).get("message").toString();
		} catch (Exception e) {
			flag = false;
		} finally {
			if (flag) {
				loggerUtil.info("验证通过");
				return joinPoint.proceed();
			} else {
				loggerUtil.error("验证未通过");
				Class returnType = method.getReturnType(); // 得到方法返回值类型
				if (returnType == String.class) { // 如果返回值为Stirng
					return "/error.jsp"; // 返回错误页面
				} else if (returnType == ModelAndView.class) {
					ModelAndView mv = new ModelAndView();
					mv.addObject("jsonData", message);
					return mv;
				} else { // 使用Ajax的时候 返回null
					return null;
				}
			}
		}
	}

	/**
	 * 验证参数是否合法
	 */
	public Map<String, Object> validateFiled(ValidateFiled[] valiedatefiles, Object[] args) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("message", "验证成功");
		

		for (ValidateFiled validateFiled : valiedatefiles) {
			Object arg = null;
			String fileName = "";
			if ("".equals(validateFiled.filedName())) {
				arg = args[validateFiled.index()];
			} else {
				arg = getFieldByObjectAndFileNameValue(args[validateFiled.index()], validateFiled.filedName());
				fileName = validateFiled.filedName();
			}

			if (validateFiled.notNull()) { // 判断参数是否为空
				if (arg == null || "".equals(arg)) {
					map.put("flag", false);
					map.put("message", fileName+"不能为空");
					return map;
				}
			} else { // 如果该参数能够为空，并且当参数为空时，就不用判断后面的了 ，直接返回true
				if (arg == null) {
					map.put("flag", true);
					map.put("message", "验证成功");
					return map;
				}
			}

			if (validateFiled.maxLen() > 0) { // 判断字符串最大长度
				if (((String) arg).length() > validateFiled.maxLen()) {
					map.put("flag", false);
					map.put("message", fileName+"最大长度为"+validateFiled.maxLen());
					return map;

				}
			}

			if (validateFiled.minLen() > 0) { // 判断字符串最小长度
				if (((String) arg).length() < validateFiled.minLen()) {
					map.put("flag", false);
					map.put("message", fileName+"最小长度为"+validateFiled.minLen());
					return map;
				}
			}

			if (validateFiled.maxVal() != -1) { // 判断数值最大值
				if ((Integer) arg > validateFiled.maxVal()) {
					map.put("flag", false);
					map.put("message", fileName+"最大值为"+validateFiled.maxVal());
					return map;
				}
			}

			if (validateFiled.minVal() != -1) { // 判断数值最小值
				if ((Integer) arg < validateFiled.minVal()) {
					map.put("flag", false);
					map.put("message", fileName+"最小值为"+validateFiled.minVal());
					return map;
				}
			}

			if (!"".equals(validateFiled.regStr())) { // 判断正则
				if (arg instanceof String) {
					if (!((String) arg).matches(validateFiled.regStr())) {
						map.put("flag", false);
						map.put("message", fileName+"中的值不符合规则");
						return map;
					}
				} else {
					map.put("flag", false);
					map.put("message", fileName+"不为String类型");
					return map;
				}
			}
		}
		return map;
	}

	/**
	 * 根据对象和属性名得到属性值
	 */
	public Object getFieldByObjectAndFileNameValue(Object targetObj, String fileName) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String tmp[] = fileName.split("\\.");
		Object arg = targetObj;
		for (int i = 0; i < tmp.length; i++) {
			Method methdo = arg.getClass().getMethod(getGetterNameByFiledName(tmp[i]));
			arg = methdo.invoke(arg);
		}
		return arg;
	}
	

	/**
	 * 根据属性名 得到该属性的getter方法名
	 */
	public String getGetterNameByFiledName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 根据目标方法和注解类型  得到该目标方法的指定注解
	 */
	public Annotation getAnnotationByMethod(Method method, Class<?> annoClass) {
		Annotation all[] = method.getAnnotations();
		for (Annotation annotation : all) {
			if (annotation.annotationType() == annoClass) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * 根据类和方法名得到方法
	 */
	public Method getMethodByClassAndName(Class<?> c, String methodName) {
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

}

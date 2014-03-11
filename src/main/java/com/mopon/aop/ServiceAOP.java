package com.mopon.aop;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

import com.mopon.util.LoggerUtil;
import com.mopon.listener.AroundListener;
/**
 * <p>Description: SERVICE AOP</p>
 * @date 2013年12月12日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Aspect
@Component("ServiceAOP")
public class ServiceAOP {
	
	private LoggerUtil logger = new LoggerUtil(ServiceAOP.class);
	
	private static List<AroundListener> listeners = new ArrayList<AroundListener>();
	
	public void addListeners(AroundListener listener) {
		listeners.add(listener);
	}
	
	@Around("execution(* com.mopon.service.impl..*.*(..))") 
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("===================around========================");
		logger.info(pjp.getTarget().getClass().getName());
		logger.info(pjp.getSignature().getName());
		for(AroundListener listener : listeners) {
			listener.aroundHandler(pjp);
		}
		return pjp.proceed();
	}
}

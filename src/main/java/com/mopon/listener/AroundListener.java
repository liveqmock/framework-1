package com.mopon.listener;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>Description: </p>
 * @date 2013年12月12日
 * @author reading.reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface AroundListener {

	public void aroundHandler(ProceedingJoinPoint jp);
}

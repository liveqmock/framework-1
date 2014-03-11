package com.mopon.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.mopon.service.sys.ISysInitService;
import com.mopon.util.LoggerUtil;

/**
 * <p>Description: 系统初始化Bean的时候调用处理</p>
 * @date 2013年9月2日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component
public class SysInitBean implements BeanPostProcessor{
	
	/**
	 * 日志记录
	 */
	protected LoggerUtil loggerUtil = new LoggerUtil(SysInitBean.class);

	/** 
	 * 方法用途: 初始BEAN前调用<br>
	 * 实现步骤: 在创建BEAN之前调用该方法<br>
	 * @param bean 对象
	 * @param beanName 名字
	 * @return 创建号的BEAN
	 * @throws BeansException   
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		
		return bean;
	}

	/** 
	 * 方法用途: 初始BEAN后调用<br>
	 * 实现步骤: 在创建BEAN之后调用该方法<br>
	 * @param bean 对象
	 * @param beanName 名字
	 * @return 创建号的BEAN
	 * @throws BeansException   
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof ISysInitService) {  
			ISysInitService sysInitService = (ISysInitService) bean;
			try {
				System.out.println("========================系统启动时进行初始化处理========================");
				sysInitService.initializer();
				System.out.println("========================初始化完成========================");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

}

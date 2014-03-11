package com.mopon.util;

/**
 * <p>Description: </p>
 * @date 2013年10月23日
 * @author REAGAN
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class DBContextHolder {
	
	 	private static final ThreadLocal<String> contextHolder =
	            new ThreadLocal<String>();
	 
	   public static void setDataSourceType(String dataSourceType) {
	      contextHolder.set(dataSourceType);
	   }
	 
	   public static String getDataSourceType() {
	      return (String) contextHolder.get();
	   }
	 
	   public static void clearDataSourceType() {
	      contextHolder.remove();
	   }
}

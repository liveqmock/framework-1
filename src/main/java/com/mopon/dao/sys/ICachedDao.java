package com.mopon.dao.sys;

/**
 * <p>Description:缓存DAO类</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ICachedDao {
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public void add(String key, Object value) throws Exception;
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */	
	public void add(String key, int export,  Object value) throws Exception;
	/** 
	 * 方法用途: 替换
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public void replace(String key, Object value) throws Exception;
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	public void replace(String key, int export, Object value) throws Exception;
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过SET添加数据时会替换掉以前的键对应的值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public void set(String key, Object value) throws Exception;
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过SET添加数据时会替换掉以前的键对应的值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	public void set(String key, int export,  Object value) throws Exception;
	/** 
	 * 方法用途: 返回值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 缓存保存的对象
	 */
	public Object get(String key) throws Exception;
	/** 
	 * 方法用途: 删除值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 删除成功为TRUE失败为FALSE
	 */
	public boolean delete(String key) throws Exception;
	/** 
	 * 方法用途: 冲突判定
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 有冲突为TRUE无为FALSE
	 */
	public boolean isMutex(String key) throws Exception;
	 /** 
	 * 方法用途: 冲突判定
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @return 有冲突为TRUE无为FALSE
	 */
	public boolean isMutex(String key, int export) throws Exception;
}

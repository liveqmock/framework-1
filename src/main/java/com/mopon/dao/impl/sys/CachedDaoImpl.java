package com.mopon.dao.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.rubyeye.xmemcached.MemcachedClient;




import com.mopon.dao.sys.ICachedDao;
/**
 * <p>Description:缓存dao</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public class CachedDaoImpl implements ICachedDao {
	
	/** 
     * 缓存时效 1天 
     */  
    public static final int CACHE_EXP_DAY = 3600 * 24;  
  
    /** 
     * 缓存时效 1周 
     */  
    public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;  
  
    /** 
     * 缓存时效 1月 
     */  
    public static final int CACHE_EXP_MONTH = 3600 * 24 * 30;  
  
    /** 
     * 缓存时效 永久 
     */  
    public static final int CACHE_EXP_FOREVER = 0;  
  
    /** 
     * 冲突延时 1秒 
     */  
    public static final int MUTEX_EXP = 1;  
    /** 
     * 冲突键 
     */  
    public static final String MUTEX_KEY_PREFIX = "MUTEX_";  

    /**
     * 缓存客户端
     */
	@Autowired
	private MemcachedClient memcachedClient;
	
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	@Override
	public void add(String key, Object value) throws Exception {
		add(key, CACHE_EXP_DAY, value);
	}
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过ADD添加数据时不允许相同键值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	@Override
	public void add(String key, int export,  Object value) throws Exception {
		memcachedClient.add(key, export, value);
	}
	
	/** 
	 * 方法用途: 替换
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public void replace(String key, Object value) throws Exception {
		replace(key, CACHE_EXP_DAY, value);
	}
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	public void replace(String key, int export, Object value) throws Exception {
		memcachedClient.replace(key, export, value);
	}
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过SET添加数据时会替换掉以前的键对应的值<br>
	 * @param key 保存键
	 * @param value 对象值
	 */
	public void set(String key, Object value) throws Exception {
		set(key, CACHE_EXP_DAY, value);
	}
	
	/** 
	 * 方法用途: 添加值
	 * 实现步骤: 通过SET添加数据时会替换掉以前的键对应的值<br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @param value 对象值
	 */
	public void set(String key, int export,  Object value) throws Exception {
		memcachedClient.set(key, export, value);
	}

	/** 
	 * 方法用途: 返回值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 缓存保存的对象
	 */
	@Override
	public Object get(String key) throws Exception {
		return memcachedClient.get(key);
	} 
	
	/** 
	 * 方法用途: 删除值
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 删除成功为TRUE失败为FALSE
	 */
	@Override
	public boolean delete(String key) throws Exception {
		return memcachedClient.delete(key);
	}
	 
	/** 
	 * 方法用途: 冲突判定
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @return 有冲突为TRUE无为FALSE
	 */
    public boolean isMutex(String key) throws Exception {  
        return isMutex(key, MUTEX_EXP);  
    }  
  
    /** 
	 * 方法用途: 冲突判定
	 * 实现步骤: <br>
	 * @param key 保存键
	 * @param export 超时时间
	 * @return 有冲突为TRUE无为FALSE
	 */
    public boolean isMutex(String key, int export) throws Exception {  
        boolean status = true;  
        if (memcachedClient.add(MUTEX_KEY_PREFIX + key, export, "true")) {  
            status = false;  
        }  
        return status;  
    }  
}

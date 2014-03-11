package com.mopon.dao.sys;


import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * <p>Description:NOSQL数据库DAO映射类</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IRepositoryDao {
	
	public void createCollection(String collectionName);
	
	public void dropCollection(String collectionName);
    
	public void save(Object entity);
	
	public void save(String collectionName, Object entity);
    
	public Object findOne(Class<?> clazz, Query query);
	
	public Object findOne(String collectionName, Class<?> clazz, Query query);
    
	public List<? extends Object> findAll(Class<?> clazz);
	
	public List<? extends Object> findAll(String collectionName, Class<?> clazz);
    
	public Object findById(Class<?> clazz,Object id);
	
	public Object findById(String collectionName, Class<?> clazz, Object id);
    
	public List<? extends Object> find(Class<?> clazz, Query query);
	
	public List<? extends Object> find(String collectionName, Class<?> clazz, Query query);
    
	public List<? extends Object> findList(Class<?> clazz,Query query,int currentPage,int pageSize);
	
	public List<? extends Object> findList(String collectionName, Class<?> clazz, Query query, int currentPage,
            int pageSize);
    
	public long findCount(Class<?> clazz,Query query);

	public long findCount(String collectionName, Query query);
    
	public int update(Query query,Update update,Class<?> clazz);
	
	public int update(String collectionName, Query query, Update update);
	
	public void remove(Object object);
	
	public void remove(String collectionName, Object object);
	
}

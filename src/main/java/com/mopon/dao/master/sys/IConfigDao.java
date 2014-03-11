package com.mopon.dao.master.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mopon.entity.sys.Config;
import com.mopon.exception.DatabaseException;

/**
 * <p>Description: 系统字典DAO</p>
 * @date 2013年9月9日
 * @author reaganjava
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface IConfigDao {
	
	/**
	 * 方法用途: 添加系统字典<br>
	 * 实现步骤: <br>
	 * @param entity 字典实体类
	 * @throws DatabaseException
	 */
	public void save(Config entity) throws DatabaseException;
	
	/**
	 * 方法用途: 获取系统字典对象<br>
	 * 实现步骤: <br>
	 * @param entity 字典实体类
	 * @throws DatabaseException
	 * @return 返回CONFIG对象
	 */
	public Config getConfig(Config entity) throws DatabaseException;

	/**
	 * 方法用途: 删除系统字典<br>
	 * 实现步骤: <br>
	 * @param entity 字典实体类
	 * @throws DatabaseException
	 */
	public void remove(Config entity) throws DatabaseException;

	/**
	 * 方法用途: 编辑系统字典<br>
	 * 实现步骤: <br>
	 * @param entity 字典实体类
	 * @throws DatabaseException
	 */
	public void update(Config entity) throws DatabaseException;

	/**
	 * 方法用途: 返回所有的系统字典<br>
	 * 实现步骤: <br>
	 * @return 系统字典的LIST列表
	 * @throws DatabaseException
	 */
	public List<Config> queryAll() throws DatabaseException;
	
	/**
	 * 
	 * 方法用途: 批量删除系统配制表<br>
	 * @param ids 系统配制表主键
	 * @throws DatabaseException
	 */
	public void remove(@Param("ids")String[] ids) throws DataAccessException;
	
	/**
	 * 
	 * 方法用途: 系统参数表分页查询<br>
	 * @param pageNo
	 * @param pageSize
	 * @param config
	 * @return
	 * @throws DataAccessException
	 */
	public List<Config> queryConfigForList(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize,@Param("config")Config config) throws DataAccessException;
	
	public int queryCount(Config config)throws DataAccessException;
	
	/**
	 * 判断键是否重复
	 * @param config
	 * @return
	 * @throws DataAccessException
	 */
	public int queryForKeyCount(Config config)throws DataAccessException;
}

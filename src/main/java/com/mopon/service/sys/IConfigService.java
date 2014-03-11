package com.mopon.service.sys;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mopon.entity.sys.Config;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.ConfigException;

public interface IConfigService {

	public void setConfig(Config config) throws ConfigException, Exception;
	
	public Config getConfig(String key) throws ConfigException, Exception;
	
	public void modifiConfig(Config config) throws ConfigException, Exception;
	
	public void removeConfig(Config config) throws ConfigException, Exception;
	
	/**
	 * 
	 * 方法用途: 批量删除系统配制<br>
	 * @param config
	 * @throws ConfigException
	 * @throws Exception
	 */
	public void removeConfig(String[] ids) throws ConfigException, DataAccessException;
	
	public List<Config> queryConfigForList() throws ConfigException, Exception;
	
	/**
	 * 
	 * 方法用途:分页查询系统配制表 <br>
	 * @param pageNum
	 * @param pageSize
	 * @param config
	 * @return
	 * @throws ConfigException
	 * @throws Exception
	 */
	public PageBean<Config> queryConfigForList(int pageNum,int pageSize,Config config) throws ConfigException, Exception;
	
	/**
	 * 判断键是否重复
	 * @param config
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryForKeyCount(Config config)throws ConfigException, Exception;
}

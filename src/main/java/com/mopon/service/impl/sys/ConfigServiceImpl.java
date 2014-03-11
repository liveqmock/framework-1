package com.mopon.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.sys.IConfigDao;
import com.mopon.entity.sys.Config;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.ConfigException;
import com.mopon.exception.DatabaseException;
import com.mopon.service.sys.IConfigService;

/**
 * 
 * <p>Description: 系统字典业务类</p>
 * @date 2013年10月11日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("configService")
public class ConfigServiceImpl extends BaseServiceImpl implements
		IConfigService {
	
	/**
	 * dao对象
	 */
	@Autowired
	private IConfigDao configDao;


	/**
	 * 方法用途: 添加系统字典<br>
	 * 实现步骤: <br>
	 * @param config 系统字典对象
	 * @throws ConfigException
	 * @throws DatabaseException
	 */
	@Override
	@Transactional
	public void setConfig(Config config) throws ConfigException, DatabaseException {
		configDao.save(config);
	}
	
	

	/**
	 * 方法用途: 修改系统字典<br>
	 * 实现步骤: <br>
	 * @param config 系统字典对象
	 * @throws ConfigException
	 * @throws DatabaseException
	 */
	@Override
	@Transactional
	public void modifiConfig(Config config) throws ConfigException, DatabaseException {
		configDao.update(config);
	}

	/**
	 * 方法用途: 删除系统字典<br>
	 * 实现步骤: <br>
	 * @param config 系统字典对象
	 * @throws ConfigException
	 * @throws DatabaseException
	 */
	@Override
	@Transactional
	public void removeConfig(Config config) throws ConfigException, DatabaseException {
		configDao.remove(config);
	}

	/**
	 * 方法用途: 返回所有字典<br>
	 * 实现步骤: <br>
	 * @return 系统字典列表
	 * @throws ConfigException
	 * @throws DatabaseException
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Config> queryConfigForList() throws ConfigException, DatabaseException {
		return configDao.queryAll();
	}

	@Override
	@Transactional
	public void removeConfig(String[] ids) throws ConfigException, DataAccessException {
		this.configDao.remove(ids);
	}

	@Override
	@Transactional(readOnly=true)
	public PageBean<Config> queryConfigForList(int pageNum, int pageSize, Config config) throws ConfigException, Exception {
		PageBean<Config> pageBean = new PageBean<Config>();
		try{
			
			pageBean.setPageSize(pageSize);
			pageBean.setPageCount(pageBean.getRecordCount());
			pageBean.setRecordCount(this.configDao.queryCount(config));
			//设置当前页
			pageBean.setCurrentPage(pageNum>pageBean.getPageCount()?(int)pageBean.getPageCount():pageNum);
			pageBean.setCurrentPage(pageNum>0?pageNum:1);
			pageNum = (pageBean.getCurrentPage()-1)*pageSize;
			pageBean.setDataList(this.configDao.queryConfigForList(pageNum, pageSize,config));//设置分页数据
		}catch(DataAccessException e){
			throw e;
		}
		return pageBean;
	}

	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param key
	 * @return
	 * @throws ConfigException
	 * @throws Exception   
	 */
	@Override
	public Config getConfig(String key) throws ConfigException, Exception {
		Config config = new Config();
		config.setKey(key);
		return configDao.getConfig(config);
	}

	/**
	 * 判断键是否重复
	 * @param config
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryForKeyCount(Config config)throws ConfigException, Exception {
		return configDao.queryForKeyCount(config);
	}
	
}

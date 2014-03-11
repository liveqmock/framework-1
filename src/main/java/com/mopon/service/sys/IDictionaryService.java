package com.mopon.service.sys;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mopon.entity.sys.Config;
import com.mopon.entity.sys.Dictionary;
import com.mopon.entity.sys.PageBean;
import com.mopon.exception.ConfigException;

public interface IDictionaryService {

	int deleteByPrimaryKey(Dictionary key);

	int insert(Dictionary record);

	int insertSelective(Dictionary record);

	List<Dictionary> getDictionary(Dictionary key);

	int updateByPrimaryKeySelective(Dictionary record);

	int updateByPrimaryKey(Dictionary record);
}

package com.mopon.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mopon.dao.master.sys.IDictionaryDao;
import com.mopon.entity.sys.Dictionary;
import com.mopon.service.sys.IDictionaryService;

/**
 * 
 * @author WangSong
 *
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl implements IDictionaryService {
	
	/**
	 * dao对象
	 */
	@Autowired
	private IDictionaryDao dictionaryDao;

	@Override
	public int deleteByPrimaryKey(Dictionary key) {
		return dictionaryDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Dictionary record) {
		return dictionaryDao.insert(record);
	}

	@Override
	public int insertSelective(Dictionary record) {
		return dictionaryDao.insertSelective(record);
	}

	@Override
	public List<Dictionary> getDictionary(Dictionary key) {
		return dictionaryDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Dictionary record) {
		return dictionaryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Dictionary record) {
		return dictionaryDao.updateByPrimaryKeySelective(record);
	}
	
	
	
}

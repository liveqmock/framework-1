package com.mopon.dao.master.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mopon.entity.sys.Dictionary;

@Repository
public interface IDictionaryDao {
	
    int deleteByPrimaryKey(Dictionary key);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    List<Dictionary> selectByPrimaryKey(Dictionary key);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}
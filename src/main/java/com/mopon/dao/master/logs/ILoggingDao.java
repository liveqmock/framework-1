package com.mopon.dao.master.logs;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mopon.entity.logs.Logging;

/**
 * <p>Description: </p>
 * @date 2013年12月24日
 * @author 王丽松
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface ILoggingDao {
	
	public Integer deleteByPrimaryKey(Long logId);

	public Integer insert(Logging record);

	public Integer insertSelective(Logging record);

	public Logging selectByPrimaryKey(Long logId);

	public Integer updateByPrimaryKeySelective(Logging record);

	public Integer updateByPrimaryKey(Logging record);
    
    public void saveBatch(List<Logging> list);
	
	public int getLoggingCount(@Param("entity")Logging entity, @Param("startDate") String startDate,@Param("endDate")  String endDate);
	
	public List<Logging> queryLoggingForList(@Param("entity")Logging entity, @Param("startDate") String startDate,@Param("endDate")  String endDate, @Param("pageNO") int pageNO, @Param("pageCount") int pageCount);

}
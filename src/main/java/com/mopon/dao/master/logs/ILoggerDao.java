package com.mopon.dao.master.logs;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mopon.entity.logs.OperateMsg;

/**
 * <p>Description: </p>
 * @date 2013年12月23日
 * @author RRR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface ILoggerDao {

	public void saveBatch(List<OperateMsg> list);
	
	public int getOperateMsgCount(@Param("entity")OperateMsg entity, @Param("startDate") String startDate,@Param("endDate")  String endDate);
	
	public List<OperateMsg> queryOperateMsgForList(@Param("entity")OperateMsg entity, @Param("startDate") String startDate,@Param("endDate")  String endDate, @Param("pageNO") int pageNO, @Param("pageCount") int pageCount);
	
}

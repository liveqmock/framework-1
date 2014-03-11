package com.mopon.dao.master;

import org.springframework.stereotype.Repository;

import com.mopon.entity.sys.TableRule;

/**
 * <p>Description: </p>
 * @date 2013年10月24日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface ITestDao {

	public void saveTest(TableRule tableRule);
	
}

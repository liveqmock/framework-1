package com.mopon.dao.slave;

import java.util.List;

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
public interface IReadTestDao {
	
	public List<TableRule> query();
}

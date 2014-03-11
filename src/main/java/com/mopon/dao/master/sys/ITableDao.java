package com.mopon.dao.master.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mopon.entity.sys.Table;
import com.mopon.entity.sys.TableRule;

/**
 * <p>Description: </p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public interface ITableDao {

	public void createTable(Map<String, String> params);
	
	public void dropTable(Map<String, String> params);
	
	public void updateUnionTable(Map<String, String> params);
	
	public void createUnionTable(Map<String, String> params);
	
	public void save(Table table);
	
	public void saveTableRule(TableRule tableRule);
	
	public void updateTableRule(TableRule tableRule);
	
	public void removeTableRule(TableRule tableRule);
	
	public void remove(Table table);
	
	public List<TableRule> queryTableRule();
	
	public List<Table> queryTableByCreateDate(Table table);
	
	public List<Table> queryTableByName(Table table);
	
	public List<Table> queryTableAll();
	
	public TableRule queryTableRuleByName(TableRule tableRule);

}

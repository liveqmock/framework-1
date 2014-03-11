package com.mopon.service.sys;

import java.util.List;

import com.mopon.entity.sys.Table;
import com.mopon.entity.sys.TableRule;

/**
 * <p>Description: 动态创建数据库表</p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface ITableService {

	/** 
	 * 方法用途: 创建表<br>
	 * 实现步骤: <br>
	 * @param tableName 表名   
	 */
	public void createTable(String sql, String tableName);
	
	/** 
	 * 方法用途: 删除表<br>
	 * 实现步骤: <br>
	 * @param tableName 表名   
	 */
	public void dropTable(String tableName);
	
	/** 
	 * 方法用途: 查询表<br>
	 * 实现步骤: <br>
	 * @param table 表对象
	 * @param where 查询方式 以时间区间或者是以表名模糊
	 * @return 表对象列表
	 */
	public List<Table> queryTable(Table table, String where);
	
	/** 
	 * 方法用途: 添加表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象 
	 */
	public void addTableRule(TableRule tableRule);
	
	/** 
	 * 方法用途: 修改表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象 
	 */
	public void modifiTableRule(TableRule tableRule);
	
	/** 
	 * 方法用途: 删除建表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象    
	 */
	public void removeTableRule(TableRule tableRule);
	
	/** 
	 * 方法用途: 返回所有建表策略<br>
	 * 实现步骤: <br>
	 * @return  策略列表 
	 */
	public List<TableRule> queryTableRule();
	
	/** 
	 * 方法用途: 返回指定表明的当前最新表信息<br>
	 * 实现步骤: <br>
	 * @return  策略列表 
	 */
	public TableRule getTablerRuleByName(TableRule tableRule);
	
	/** 
	 * 方法用途: 开始执行任务<br>
	 * 实现步骤: <br>
	 * 
	 */
	public void startCreateTableTask();
}

package com.mopon.service.impl.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mopon.dao.master.sys.ITableDao;
import com.mopon.entity.sys.Table;
import com.mopon.entity.sys.TableRule;
import com.mopon.service.sys.ITableService;
import com.mopon.timer.ITaskHandler;
import com.mopon.timer.TaskJob;

/**
 * <p>Description: </p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("tableService")
public class TableServiceImpl extends BaseServiceImpl implements ITableService, ITaskHandler {
	
	@Autowired
	private ITableDao tableDao;

	/** 
	 * 方法用途: 创建表<br>
	 * 实现步骤: <br>
	 * @param tableName 表名   
	 */
	@Override
	@Transactional
	public void createTable(String sql, String tableName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("createSQL", sql);
		tableDao.createTable(params);
		Table table = new Table();
		table.setTableName(tableName);
		table.setCreateDate(new Date());
		table.setStatus(0);
		tableDao.save(table);
	}

	/** 
	 * 方法用途: 删除表<br>
	 * 实现步骤: <br>
	 * @param tableName 表名   
	 */
	@Override
	@Transactional
	public void dropTable(String tableName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", tableName.toUpperCase());
		tableDao.dropTable(params);
		Table table = new Table();
		table.setTableName(tableName);
		tableDao.remove(table);
	}

	/** 
	 * 方法用途: 查询表<br>
	 * 实现步骤: <br>
	 * @param table 表对象
	 * @param where 查询方式 以时间区间或者是以表名模糊
	 * @return 表对象列表
	 */
	@Override
	public List<Table> queryTable(Table table, String where) {
		switch(where) {
		case "tableName": {
			return tableDao.queryTableByName(table);
		}
		case "create_date":
			return tableDao.queryTableByCreateDate(table);
		}
		return null;
	}

	/** 
	 * 方法用途: 添加表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象 
	 */
	@Override
	public void addTableRule(TableRule tableRule) {
		Map<String, String> params = new HashMap<String, String>();
		tableDao.saveTableRule(tableRule);
		String sql = tableRule.getUnionCreateSQL();
		params.put("createUnionSQL", sql);
		tableDao.createUnionTable(params);
	}

	/** 
	 * 方法用途: 修改表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象 
	 */
	@Override
	public void modifiTableRule(TableRule tableRule) {
		tableDao.updateTableRule(tableRule);
	}

	/** 
	 * 方法用途: 删除建表策略<br>
	 * 实现步骤: <br>
	 * @param tableRule 策略对象    
	 */
	@Override
	public void removeTableRule(TableRule tableRule) {
		tableDao.removeTableRule(tableRule);
	}

	/** 
	 * 方法用途: 返回所有建表策略<br>
	 * 实现步骤: <br>
	 * @return  策略列表 
	 */
	@Override
	public List<TableRule> queryTableRule() {
		return tableDao.queryTableRule();
	}
	
	/** 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param tableRule
	 * @return   
	 */
	@Override
	public TableRule getTablerRuleByName(TableRule tableRule) {
		return tableDao.queryTableRuleByName(tableRule);
	}

	
	
	/** 
	 * 方法用途: 开始执行任务<br>
	 * 实现步骤: <br>
	 * 
	 */
	@Override
	public void startCreateTableTask() {
		TaskJob.addTaskHandler(this);
	}

	/** 
	 * 方法用途: 定时建立数据库表定时实现<br>
	 * 实现步骤: <br>   
	 */
	@Override
	public void execute() {
		//得到建表策略
		List<TableRule> tableRuleList = tableDao.queryTableRule();
		loggerUtil.info("execute createa table");
		Map<String, String> params = new HashMap<String, String>();
		for(TableRule tableRule : tableRuleList) {
			//得到最新建表时间
			long startDate = tableRule.getStartDate().getTime();
			//当前时间
			long currentDate = System.currentTimeMillis();
			
			loggerUtil.info(startDate + " " + currentDate + " " + (currentDate - startDate));
			//达到建表的间隔时间
			if((currentDate - startDate) > tableRule.getInterval()) {
				Date date = new Date();
				//用时间作为建表键值
				String tableName = tableRule.getTableName() + "_" + System.currentTimeMillis();
				
				loggerUtil.info("====== tableName " + tableName + "======");
				String sql = tableRule.getCreateSQL();
				
				loggerUtil.info("====== createSQL " + sql + "======");
				
				sql = sql.replace("[#####]", tableName);
				
				loggerUtil.info("====== createSQL " + sql + "======");
				
				List<Table> tableList = tableDao.queryTableAll();
				
				String allTableName = "";
				for(Table t : tableList) {
					allTableName += (t.getTableName() + ",");
				}
				
				allTableName += tableName;
				String unionUpdateSQL = tableRule.getUnionUpdateSQL();
				unionUpdateSQL = unionUpdateSQL.replace("[$$$$$]", allTableName);
				loggerUtil.info("====== unionUpdateSQL " + unionUpdateSQL + "======");
				this.createTable(sql, tableName);
				
				params.put("unionUpdateSQL", unionUpdateSQL);
				tableDao.updateUnionTable(params);
				tableRule.setStartDate(date);
				//更新建表时间
				this.modifiTableRule(tableRule);
				tableName = "";
			}
		}
	}

	
	

}

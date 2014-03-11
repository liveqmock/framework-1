package com.mopon.entity.sys;

import java.util.Date;

/**
 * <p>Description: 表策略建立</p>
 * @date 2013年10月17日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class TableRule {

	private Integer id;
	
	private String tableName;
	
	private Date startDate;
	
	private Integer interval;
	
	private String unionTable;
	
	private String createSQL;
	
	private String unionCreateSQL;
	
	private String unionUpdateSQL;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getUnionTable() {
		return unionTable;
	}

	public void setUnionTable(String unionTable) {
		this.unionTable = unionTable;
	}

	public String getCreateSQL() {
		return createSQL;
	}

	public void setCreateSQL(String createSQL) {
		this.createSQL = createSQL;
	}

	public String getUnionCreateSQL() {
		return unionCreateSQL;
	}

	public void setUnionCreateSQL(String unionCreateSQL) {
		this.unionCreateSQL = unionCreateSQL;
	}

	public String getUnionUpdateSQL() {
		return unionUpdateSQL;
	}

	public void setUnionUpdateSQL(String unionUpdateSQL) {
		this.unionUpdateSQL = unionUpdateSQL;
	}
	
}

package com.mopon.entity.logs;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: </p>
 * @date 2013年12月23日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum OPType {

	ADD(0),
	DELETE(1),
	UPDATE(2),
	QUERY(3),
	LOGIN(4),
	LOGOUT(5);
	
	private int value;
	
	private Map<Integer, String> opTypeMsg = new HashMap<Integer, String>();
	
	private OPType(int value) {
		this.value = value;
		opTypeMsg.put(0, "添加操作");
		opTypeMsg.put(1, "删除操作");
		opTypeMsg.put(2, "更新操作");
		opTypeMsg.put(3, "查询操作");
		opTypeMsg.put(4, "登录系统");
		opTypeMsg.put(5, "登出系统");
	}
	
	public int getOpType() {
		return this.value;
	}
	
	public String getOpTypeMsg() {
		return opTypeMsg.get(value);
	}
}

package com.mopon.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * <p>Description: JSON工具</p>
 * @date 2013年10月25日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("jsonTools")
public class JSONTools<T> {
	
	/**
	 * 方法用途: 生成对象JSON<br>
	 * 实现步骤: <br>
	 * @param t 类对象
	 * @return 字符串JSON
	 */
	public String writeObject(T t) {
		JSONObject jsonObject = JSONObject.fromObject(t);
		return jsonObject.toString();
	}
	
	/**
	 * 方法用途: 生成对象数组JSON<br>
	 * 实现步骤: <br>
	 * @param t 类对象数组
	 * @return 字符串JSON
	 */
	public String writeArray(T[] t) {
		JSONArray jsonArray = JSONArray.fromObject(t);
		return jsonArray.toString();
	}
	
	/**
	 * 方法用途: 生成对象Map JSON<br>
	 * 实现步骤: <br>
	 * @param map 集合对象
	 * @return 字符串JSON
	 */
	public String writeMap(Map<Object, T> map) {
		JSON json = JSONSerializer.toJSON(map);
		return json.toString();
	}
	
	/**
	 * 方法用途: 生成对象List JSON<br>
	 * 实现步骤: <br>
	 * @param list 集合对象
	 * @return 字符串JSON
	 */
	public String writeList(List<T> list) {
		JSON json = JSONSerializer.toJSON(list);
		return json.toString();
	}
	
	/**
	 * 方法用途: 通过JSON创建对象<br>
	 * 实现步骤: <br>
	 * @param json 字符串数据
	 * @param clazz 对应的类
	 * @return 对象
	 */
	public T readObject(String json, Class<T> clazz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return (T) JSONObject.toBean(jsonObject, clazz);
	}
	
	/**
	 * 方法用途: 通过JSON创建对象数组<br>
	 * 实现步骤: <br>
	 * @param json 字符串数据
	 * @param clazz 对应的类
	 * @return 对象数组
	 */
	public T[] readArray(String json, Class<T> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return (T[]) JSONArray.toArray(jsonArray, clazz);
	}
	
	/**
	 * 方法用途: 通过JSON创建对象MAP<br>
	 * 实现步骤: <br>
	 * @param json 字符串数据
	 * @param clazz 对应的类
	 * @return MAP
	 */
	public Map<Object, T> readMap(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return (Map<Object, T>) JSONObject.toBean(jsonObject, Map.class);
	}
	
	/**
	 * 方法用途: 通过JSON创建对象List<br>
	 * 实现步骤: <br>
	 * @param json 字符串数据
	 * @param clazz 对应的类
	 * @return List
	 */
	public List<T> readList(String json, Class<T> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return (List<T>) JSONArray.toCollection(jsonArray, clazz);
	}

}

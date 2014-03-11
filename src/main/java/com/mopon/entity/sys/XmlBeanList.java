package com.mopon.entity.sys;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>Description: 返回XML集合</p>
 * <p>在使用该类的时候要将放入容器的对象在SPRING-SERVLET的classesToBeBound中配置</p>
 * @date 2013年10月9日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@XmlRootElement(name="values")  
public class XmlBeanList<T> {
	
	/**
	 * 集合结果数
	 */
	private int recordCount;
	
	/**
	 * 当前页数
	 */
	private int count;

	/**
	 * 开始位置
	 */
	private int start;

	/**
	 * 结束位置
	 */
	private int end;
	
	/**
	 * 页数
	 */
	private int pageSize;

	/**
	 * 当前页数
	 */
	private int currentPage;
	
	/**
	 * 集合容器
	 */
	List<T> items = new ArrayList<T>();
	
	public int getTotalCount() {
		return recordCount;
	}
	
	@XmlAttribute
	public void setTotalCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	public int getCount() {
		return count;
	}
	
	@XmlAttribute
	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	@XmlAttribute
	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	@XmlAttribute
	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageSize() {
		return pageSize;
	}

	@XmlAttribute
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	@XmlAttribute
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getItems() {
		return items;
	}

	@XmlElementWrapper(name="items")
    @XmlElement(name="item")
	public void setItems(List<T> items) {
		this.items = items;
	}
	
}

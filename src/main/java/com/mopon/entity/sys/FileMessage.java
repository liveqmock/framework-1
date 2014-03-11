package com.mopon.entity.sys;

import java.util.Date;
import java.util.UUID;
/**
 * 
 * <p>Description: 文件实体类</p>
 * @date 2013年9月24日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class FileMessage {
	
	
	private String _id;
	 
	/**
	 * 文件ID
	 */
	private String fid = UUID.randomUUID().toString();
	
	/**
	 * 文件名
	 */
	private String filename;
	
	/**
	 * 存储路径
	 */
	private String url;
	
	/**
	 * 文件类型
	 */
	private String type;
	
	/**
	 * 存储路径
	 */
	private String path;
	
	/**
	 * 创建时间
	 */
	private Date date;
	
	/**
	 * 上传类型
	 */
	private String upType;
	
	/**
	 * 文件大小
	 */
	private long length;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUpType() {
		return upType;
	}

	public void setUpType(String upType) {
		this.upType = upType;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}
	
}

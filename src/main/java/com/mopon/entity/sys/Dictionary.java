package com.mopon.entity.sys;

/**
 * 数据字典
 * @author WangSong
 *
 */
public class Dictionary {
	
	/**
	 * 代码编号
	 */
	private String codeId;

	/**
	 * 代码名称
	 */
	private String codeName;

	/**
	 * 代码类型
	 */
	private String codeType;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
	private String remark;
	
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName == null ? null : codeName.trim();
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType == null ? null : codeType.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}
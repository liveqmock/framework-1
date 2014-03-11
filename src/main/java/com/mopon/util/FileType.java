package com.mopon.util;

public enum FileType {
	IMAGE("image", 1), FACE("face", 2), AUDIO("audio", 3), DOCUMENT("document", 4);

	// 成员变量
	private String name;
	private int type;

	// 构造方法
	private FileType(String name, int type) {
		this.name = name;
		this.type = type;
	}

	// 普通方法
	public static String getName(int type) {
		for (FileType c : FileType.values()) {
			if (c.getType() == type) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

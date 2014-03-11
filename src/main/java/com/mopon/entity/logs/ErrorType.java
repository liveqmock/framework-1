package com.mopon.entity.logs;

public enum ErrorType {

	SYSTEM_ERROR(1),
	
	APPLICATION_ERROR(2),
	
	OPERATION_ERROR(3);
	
	private final int type;
	
	private ErrorType(int type) {
		this.type = type;
	}
	
	public int value() {
		return this.type;
	}
	
	public static ErrorType valueOf(int type) {
		for (ErrorType status : values()) {
			if (status.type == type) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + type + "]");
	}
}

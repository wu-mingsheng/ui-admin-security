package com.boe.admin.uiadmin.enums;

public enum SyncStateEnum {
	
	HAS("已同步", 1), NOT("未同步", 0);
	
	
	
	private SyncStateEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	private String msg;
	
	private Integer code;

	public String getMsg() {
		return msg;
	}

	public Integer getCode() {
		return code;
	}
	
	public Integer value() {
		return code;
	}
	
	

}

package com.boe.admin.uiadmin.common;

import com.boe.admin.uiadmin.enums.ResultCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
	
	private T data;
	
	private String message;
	
	private Integer code;
	
	public static <T> Result<T> of(T data, String msg, Integer code) {
		
		return new Result<>(data, msg, code);
	} 
	

	public static <T> Result<T> of(T data, ResultCodeEnum stateCodeEnum) {
		
		return new Result<>(data, stateCodeEnum.getMessage(), stateCodeEnum.getCode());
	} 

}

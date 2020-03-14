package com.boe.admin.uiadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
@Getter
public enum ResultCodeEnum {
	
	SUCCESS(0, "SUCCESS"),//请求成功
	USERNAME_NOT_EXIST(50001, "用户名不存在"),
	PASSWORD_NOT_CORRECT(50002, "密码错误"),
	USERNAME_OR_PASSWORD_ERR(50003, "用户名或密码错误"),
	ILLEGAL_TOKEN(50008, "Illegal token"),//非法token
	OTHER_CLIENTS_LOGGED_IN(50012, "Other clients logged in"),//其他终端登录
	TOKEN_EXPIRED(50014, "Token expired"),//token过期
	ERROR(-1, "ERROR");//请求失败
	
	private Integer code;
	
	private String message;
	
	

}

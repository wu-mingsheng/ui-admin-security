package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginVo {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;

}

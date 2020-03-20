package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserVo {
	
	private Long idLong;
	
	@NotBlank(message = "用户名不能为空")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
	@NotNull(message = "没有指定角色")
	private Long roleId;

}

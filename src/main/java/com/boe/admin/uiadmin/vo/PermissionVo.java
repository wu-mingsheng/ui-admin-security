package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PermissionVo {
	
	@NotBlank(message = "url不能为空")
	private String url;
	@NotBlank(message = "name不能为空")
	private String name;
	@NotBlank(message = "描述信息不能为空")
	private String description;
	@NotNull(message = "pid不能为空")
	private Long pid;

}

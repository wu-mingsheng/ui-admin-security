package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RoleVo {
	
	
	@NotNull
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String alias;

}

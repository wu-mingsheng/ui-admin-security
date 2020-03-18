package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InstitutionListVo {
	
	@NotNull(message = "page not null")
	@Min(value = 1, message = "page must > 1")
	private Integer page;
	
	@NotNull(message = "pageSize not null")
	@Min(value = 1, message = "pageSize must > 1")
	private Integer pageSize;
	
	private String name;
	
	private String category;
	
	private String state; 

}

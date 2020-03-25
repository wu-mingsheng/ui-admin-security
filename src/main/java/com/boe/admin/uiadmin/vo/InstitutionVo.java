package com.boe.admin.uiadmin.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.boe.admin.uiadmin.valid.group.AddInstitutionGroup;
import com.boe.admin.uiadmin.valid.group.ListInistitutionGroup;
import com.boe.admin.uiadmin.valid.group.UpdateInstitutionGroup;

import lombok.Data;

@Data
public class InstitutionVo {
	
	@NotNull(message = "page not null", groups = ListInistitutionGroup.class)
	@Min(value = 1, message = "page must > 1", groups = ListInistitutionGroup.class)
	private Integer page;
	
	@NotNull(message = "pageSize not null", groups = ListInistitutionGroup.class)
	@Min(value = 1, message = "pageSize must > 1", groups = ListInistitutionGroup.class)
	private Integer pageSize;
	
	@NotNull(message = "id not null", groups = UpdateInstitutionGroup.class)
	private Long id;
	
	@NotBlank(message = "name not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private String name;
	@NotBlank(message = "category not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private String category;
	@NotBlank(message = "state not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private String state;
	@NotNull(message = "accountNum not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private Integer accountNum;
	@NotNull(message = "bandWidth not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private Integer bandWidth;
	@NotNull(message = "diskSpace not null", groups = {AddInstitutionGroup.class, UpdateInstitutionGroup.class})
	private Integer diskSpace;

}

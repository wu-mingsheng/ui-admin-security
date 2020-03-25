package com.boe.admin.uiadmin.vo;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.boe.admin.uiadmin.valid.group.AddAuthorizationGroup;
import com.boe.admin.uiadmin.valid.group.ListAuthorizationGroup;
import com.boe.admin.uiadmin.valid.group.ListInistitutionGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuthorizationVo {
	
	

	@NotNull(message = "page not null", groups = ListAuthorizationGroup.class)
	@Min(value = 1, message = "page must > 1", groups = ListInistitutionGroup.class)
	private Integer page;
	
	

	@NotNull(message = "pageSize not null", groups = ListAuthorizationGroup.class)
	@Min(value = 1, message = "pageSize must > 1", groups = ListInistitutionGroup.class)
	private Integer pageSize;
	
	private String name;
	
	private String state;
	
	
	@NotNull(message = "ahtuorizeNum not null", groups = {AddAuthorizationGroup.class})
	private Integer authorizeNum;
	
	@NotNull(message = "institutionId not null", groups = {AddAuthorizationGroup.class})
	private Long institutionId;
	
	@NotNull(message = "startTime not null", groups = {AddAuthorizationGroup.class})
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	
	@NotNull(message = "endTime not null", groups = {AddAuthorizationGroup.class})
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

}

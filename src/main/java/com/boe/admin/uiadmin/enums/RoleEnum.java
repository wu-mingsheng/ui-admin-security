package com.boe.admin.uiadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
	
	ADMIN("系统管理员", "admin", 0), NORMAL("普通用户", "view", 1);
	
	private String roleDesc;
	
	private String roleName;
	
	private Integer roleId;
	
	public static RoleEnum roleEnum(Integer roleId) {
		RoleEnum[] values = RoleEnum.values();
		for (RoleEnum roleEnum : values) {
			if(roleEnum.getRoleId() == roleId) {
				return roleEnum;
			}
		}
		
		return null;
	}

}

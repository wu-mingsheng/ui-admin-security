package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@Data
@TableName("role_permission")
public class RolePermissionPo {
	
	private Long roleId;
	
	private Long permissionId;

}

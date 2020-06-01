package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@TableName("role_permission")
@EqualsAndHashCode(callSuper = true)
public class RolePermissionPo extends BasePo{
	

	
	private Long roleId;
	
	private Long permissionId;

}

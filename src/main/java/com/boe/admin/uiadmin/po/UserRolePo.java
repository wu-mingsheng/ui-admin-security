package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_role")
public class UserRolePo {
	
	private Long userId;
	
	private Long roleId;

}

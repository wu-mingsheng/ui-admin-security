package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_role")
public class UserRolePo {
	
	@TableId
	private Long id;
	
	private Long userId;
	
	private Long roleId;

}

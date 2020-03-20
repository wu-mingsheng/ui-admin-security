package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRolePo extends BasePo{
	
	@TableId
	private Long id;
	
	private Long userId;
	
	private Long roleId;

}

package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRolePo extends BasePo{
	

	
	private Long userId;
	
	private Long roleId;

}

package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("role")
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePo extends BasePo {
	


	
	private String name;
	
	private String alias;

}

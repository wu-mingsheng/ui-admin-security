package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("role")
@Data
public class RolePo {
	

	@TableId
	private Long id;
	
	private String name;

}

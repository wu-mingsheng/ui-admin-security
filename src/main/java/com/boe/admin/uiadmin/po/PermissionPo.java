package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("permission")
public class PermissionPo {
	@TableId
	private Long id;
	
	private String url;
	
	private String name;
	
	private String description;
	
	private Long pid;

}

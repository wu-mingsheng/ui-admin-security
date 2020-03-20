package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionPo extends BasePo {
	@TableId
	private Long id;
	
	private String url;
	
	private String name;
	
	private String description;
	
	private Long pid;

}

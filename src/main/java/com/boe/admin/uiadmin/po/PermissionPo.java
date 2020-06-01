package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@TableName("permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionPo extends BasePo {

	
	private String url;
	
	private String name;
	
	private String description;
	
	private Long pid;
	
	@TableField(exist = false)
	private List<PermissionPo> children;

}

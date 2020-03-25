package com.boe.admin.uiadmin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("institution")
@Data
@EqualsAndHashCode(callSuper = true)
public class InstitutionPo extends BasePo {
	
	
	
	
	
	private String name;

	private String category;
	
	/**0:未同步 1:已同步*/
	private Integer syncState;
	
	private LocalDateTime syncTime;
	
	private Integer accountNum;
	
	private Integer bandWidth;
	
	private Integer diskSpace;
	
	
	

}

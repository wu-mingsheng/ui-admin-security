package com.boe.admin.uiadmin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.boe.admin.uiadmin.enums.StateEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstitutionPo {
	
	@TableId
	private Integer id;
	
	private Integer createrId;
	
	private Integer updaterId;
	
	private LocalDateTime createTime;
	
	private LocalDateTime updateTime;
	
	private StateEnum state;
	
	//======================业务字段==============================
	
	private String name;
	
	//private Integer authorizedNum;
	
	private String category;
	
	private Integer syncState;
	
	private LocalDateTime syncTime;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

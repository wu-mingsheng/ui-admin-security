package com.boe.admin.uiadmin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("authorization")
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizationPo extends BasePo {
	
	private Integer authorizeNum;
	
	private Long institutionId;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;

}

package com.boe.admin.uiadmin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.boe.admin.uiadmin.enums.StatusEnum;

import lombok.Data;

@Data
public class BasePo {
	
	
	@TableId
	private Integer id;
	
	private Integer createrId;
	
	private Integer updaterId;
	
	private LocalDateTime createTime;
	
	private LocalDateTime updateTime;
	
	private StatusEnum status;

}

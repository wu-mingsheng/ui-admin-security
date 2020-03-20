package com.boe.admin.uiadmin.po;

import static com.boe.admin.uiadmin.enums.StateEnum.ENABLED;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.boe.admin.uiadmin.enums.StateEnum;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.utils.UserUtil;

import lombok.Data;

@Data
public class BasePo {
	
	
	@TableId
	private Long id;
	
	private Long createrId;
	
	private Long updaterId;
	
	private LocalDateTime createTime;
	
	private LocalDateTime updateTime;
	
	private StateEnum state;
	
	public void setBasePoFields() {
		LocalDateTime now = DateUtil.now();
		Long currentUserId = UserUtil.getCurrentUserId();
		this.setCreateTime(now);
		this.setUpdateTime(now);
		this.setCreaterId(currentUserId);
		this.setUpdaterId(currentUserId);
		this.setState(ENABLED);
	}

}

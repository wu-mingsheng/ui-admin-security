package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@TableName("user")
@Data
public class UserPo {
	
	@TableId
	private Long id;
	
	private String username;
	 
    private String password;
 
    
 
    

}

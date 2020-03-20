package com.boe.admin.uiadmin.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;


@TableName("user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPo extends BasePo{

	
	private String username;
	 
    private String password;
 
    
 
    

}

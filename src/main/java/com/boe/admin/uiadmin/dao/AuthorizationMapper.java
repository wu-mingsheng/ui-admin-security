package com.boe.admin.uiadmin.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boe.admin.uiadmin.po.AuthorizationPo;


@Repository
public interface AuthorizationMapper extends BaseMapper<AuthorizationPo> {
	
	
	public Integer getTotalAuthorizeNum(); 

}

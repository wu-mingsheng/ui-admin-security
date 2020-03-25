package com.boe.admin.uiadmin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.AuthorizationMapper;
import com.boe.admin.uiadmin.dao.InstitutionMapper;
import com.boe.admin.uiadmin.enums.ResultCodeEnum;
import com.boe.admin.uiadmin.enums.StateEnum;
import com.boe.admin.uiadmin.po.InstitutionPo;
import com.google.common.collect.Maps;

@Service
public class DashboardService {
	
	@Autowired
	private InstitutionMapper institutionMapper;
	
	@Autowired
	private AuthorizationMapper authorizationMapper;

	public Result<Object> getDashboardData() {
		LambdaQueryWrapper<InstitutionPo> queryWrapper = Wrappers.<InstitutionPo>lambdaQuery()
											.ne(InstitutionPo::getState, StateEnum.DELETED);
		Integer institutionNum = institutionMapper.selectCount(queryWrapper);
		
		Integer totalAuthorizeNum = authorizationMapper.getTotalAuthorizeNum();
		
		Map<Object,Object> map = Maps.newHashMap();
		map.put("inistitutionNum", institutionNum);
		map.put("screenNum", totalAuthorizeNum);
		
		return Result.of(map, ResultCodeEnum.SUCCESS);
		
	}

}

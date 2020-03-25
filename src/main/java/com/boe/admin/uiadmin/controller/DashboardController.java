package com.boe.admin.uiadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.AuthorizationService;
import com.boe.admin.uiadmin.service.DashboardService;
import com.boe.admin.uiadmin.valid.group.AddAuthorizationGroup;
import com.boe.admin.uiadmin.valid.group.ListAuthorizationGroup;
import com.boe.admin.uiadmin.vo.AuthorizationVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("dashboard")
@Slf4j
public class DashboardController {
	
	
	@Autowired
	private DashboardService dashboardService;

	@GetMapping("getDashboardData")
	public Result<Object> getDashboardData() throws Exception {
		return dashboardService.getDashboardData();
	}
	 

}

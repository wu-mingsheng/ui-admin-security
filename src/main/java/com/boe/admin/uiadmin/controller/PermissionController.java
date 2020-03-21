package com.boe.admin.uiadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("permission")
@Slf4j
@Validated
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("list")
	public Result<Object> listPermissions() throws Exception {
		
		
		return permissionService.permissionTree();
		
		
	}

}

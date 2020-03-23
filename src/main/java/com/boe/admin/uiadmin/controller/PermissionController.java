package com.boe.admin.uiadmin.controller;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.PermissionService;
import com.boe.admin.uiadmin.vo.PermissionVo;
import com.google.common.base.Preconditions;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("permission")
@Slf4j
@Validated
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@PostMapping("addRolePermission")
	public Result<Object> addRolePermission(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining());
		String roleId = JsonCode.getValue(body, "$.roleId");
		String[] permissionIds = JsonCode.getValueList(body, "$.permissionIds");
		
		log.info(" === request body is : [roleId: {}, permissionIds: {}]", roleId, permissionIds);
		
		Preconditions.checkState(StringUtils.isNotBlank(roleId), "roleId 不能为空");
		Set<Long> permisssionIdSet = Stream.of(permissionIds).filter(StringUtils::isNotBlank).map(Long::parseLong).collect(Collectors.toSet());
		
		
		
		return permissionService.addRolePermission(Long.parseLong(roleId), permisssionIdSet);
		
		
	}
	
	@GetMapping("list")
	public Result<Object> listPermissions() throws Exception {
		
		
		return permissionService.permissionTree();
		
		
	}
	
	@GetMapping("role")
	public Result<Object> rolePermission(@RequestParam("roleId")Long roleId) throws Exception {
		log.info(" ===  request params is : [roleId: {}]", roleId);
		
		return permissionService.rolePermission(roleId);
		
		
	}
	
	@PostMapping("add")
	public Result<Object> addPermission(@RequestBody @Valid PermissionVo permissionVo) throws Exception {
		log.info(" === request body is : [{}]", permissionVo);
		
		return permissionService.addPermission(permissionVo);
	}
	
	@DeleteMapping("delete/{id}")
	public Result<Void> deletePermission(@PathVariable("id") Long id) throws Exception {
		log.info(" ===  request path url is : [{}]", id);
		return permissionService.deletePermission(id);
	}
	
	@PutMapping("update")
	public Result<Void> updatePermission(@RequestBody @Valid PermissionVo permissionVo) throws Exception {
		log.info(" === request body is : [{}]", permissionVo);
		
		return permissionService.updatePermission(permissionVo);
	}

}

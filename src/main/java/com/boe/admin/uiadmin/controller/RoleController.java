package com.boe.admin.uiadmin.controller;

import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.RoleService;
import com.boe.admin.uiadmin.vo.RoleVo;
import com.google.common.base.Preconditions;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("role")
@Slf4j
@Validated
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("listAll")
	public Result<Object> listAll() throws Exception {
		return roleService.listAll();
	}
	
	@PostMapping("add")
	public Result<Object> addRole(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining());
		String name = JsonCode.getValue(body, "$.name");
		String alias = JsonCode.getValue(body, "$.alias");
		log.info(" === request body is : [name: {}, alias: {}]", name, alias);
		Preconditions.checkState(StringUtils.isNotBlank(name), "name 不能为空");
		Preconditions.checkState(StringUtils.isNotBlank(alias), "alias 不能为空");
		return roleService.addRole(name, alias);
	}
	
	@DeleteMapping("delete/{id}")
	public Result<Object> deleteRole(@PathVariable("id") Long id) throws Exception {
		log.info(" === request path url is : [{}]", id);
		return roleService.deleteRole(id);
	}
	
	@PutMapping("update")
	public Result<Object> updateRole(@RequestBody @Valid RoleVo roleVo) throws Exception {
		log.info(" === request body is : [{}]", roleVo);
		return roleService.updateRole(roleVo);
	}

}

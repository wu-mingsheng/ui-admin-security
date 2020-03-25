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
import com.boe.admin.uiadmin.valid.group.AddAuthorizationGroup;
import com.boe.admin.uiadmin.valid.group.ListAuthorizationGroup;
import com.boe.admin.uiadmin.vo.AuthorizationVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("authorization")
@Slf4j
public class AuthorizationController {
	
	@Autowired
	private AuthorizationService authorizationService;
	
	 @GetMapping("orders")
	 public Result<Object> orders(@RequestParam(value = "id") Long id) throws Exception{
		 log.info(" ========== request params is : [id : {}]", id);
		 return authorizationService.orders(id);
	 }
	 
	 @PostMapping("add")
	 public Result<Object> add(@RequestBody @Validated(AddAuthorizationGroup.class) AuthorizationVo authorizationVo) 
			 throws Exception {
		 
		 log.info(" === request body is : [{}]", authorizationVo);
		 return authorizationService.addAuthorization(authorizationVo);
		 
	 }
	 
	 
	 
	 @GetMapping("list")
	 public Result<Object> list(@Validated(ListAuthorizationGroup.class)AuthorizationVo authorizationVo) 
			 throws Exception {
		 
		 log.info(" ===  request params is : [{}]", authorizationVo);
		 
		 return authorizationService.list(authorizationVo);
		 
	 }
	 

}

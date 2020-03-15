package com.boe.admin.uiadmin.controller;


import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.LoginService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private LoginService loginService;


  
    @PostMapping(value = "login")
    public Result<Object> login(HttpServletRequest request) throws Exception {
    	
    	String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info("=== requestbody is : [{}]", body);
		String username = JsonCode.getValue(body, "$.username");
		String password = JsonCode.getValue(body, "$.password");
		Preconditions.checkNotNull(username, "username not null");
		Preconditions.checkNotNull(password, "password not null");
        // 登录成功会返回Token给用户
        String token = loginService.login( username, password );
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Result.of(data, "登录成功", 200);
    }
    
   /**
    * 用户登出返回结果
    * 这里应该让前端清除掉Token
    */
    @PostMapping("logout")
    public Result<Void> logout() throws Exception {
    	 
         SecurityContextHolder.clearContext();
         
         return Result.of(null, "登出成功", 200);
    }

    @PostMapping(value = "/user/hi")
    @PreAuthorize("hasRole('root')")
    public String userHi( String name ) throws AuthenticationException {
        return "hi " + name + " , you have 'user' role";
    }

    @PostMapping(value = "/admin/hi")
    @PreAuthorize("hasPermission('/user/admin/hi','/user/admin/hi')")
    public String adminHi( String name ) throws AuthenticationException {
        return "hi " + name + " , you have 'admin' role";
    }


}
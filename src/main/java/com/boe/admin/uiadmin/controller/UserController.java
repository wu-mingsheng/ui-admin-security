package com.boe.admin.uiadmin.controller;


import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.service.LoginService;
import com.google.common.base.Preconditions;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private LoginService loginService;


  
    @PostMapping(value = "login")
    public String login(HttpServletRequest request) throws Exception {
    	
    	String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info("=== requestbody is : [{}]", body);
		String username = JsonCode.getValue(body, "$.username");
		String password = JsonCode.getValue(body, "$.password");
		Preconditions.checkNotNull(username, "username not null");
		Preconditions.checkNotNull(password, "password not null");
        // 登录成功会返回Token给用户
        return loginService.login( username, password );
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
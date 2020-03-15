package com.boe.admin.uiadmin.controller;


import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
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



    /**
     * 通过用户名和密码登录
     */
    @PostMapping(value = "login")
    public Result<Object> login(HttpServletRequest request) throws Exception {
    	
    	String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info("=== requestbody is : [{}]", body);
		String username = JsonCode.getValue(body, "$.username");
		String password = JsonCode.getValue(body, "$.password");
		Preconditions.checkNotNull(username, "username not null");
		Preconditions.checkNotNull(password, "password not null");
        // 登录成功会返回Token给用户
        return loginService.login( username, password );
       
    }
    
    

    /**
     * 通过token获取用户信息
     */
	@GetMapping("info")
	public Result<Object> info(@RequestParam(name = "token", required = true) String token) throws Exception {

		log.info("=== get user info: [token={}]", token);
		
		return loginService.info(token);

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

    


}
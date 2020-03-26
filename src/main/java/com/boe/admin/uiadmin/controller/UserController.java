package com.boe.admin.uiadmin.controller;


import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.LoginService;
import com.boe.admin.uiadmin.service.UserService;
import com.boe.admin.uiadmin.vo.UserVo;
import com.google.common.base.Preconditions;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

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

    /**
     * 用户列表
     */
    @GetMapping("list")
    public Result<Object> list(@RequestParam(value = "username", required = false)String username, 
    		@RequestParam(value = "page", required = true)Long page,
    		@RequestParam(value = "pageSize", required = true)Long pageSize) throws Exception {
    	
    	log.info(" === request params is : [username={},page={},pageSize={}]", username, page, pageSize);
    	return userService.listUsers(username, page, pageSize);
    	
    }
    
    
    /**
     * 添加用户
     */
    @PostMapping("add")
    public Result<Object> addUser(@RequestBody @Valid UserVo userVo) throws Exception {
    	
    	log.info(" === request body is : [{}]", userVo);
    	
    	return userService.addUser(userVo);

    	
    }
    

    /**
     * 删除用户
     */
    @PostMapping("delete")
    public Result<Object> deleteUser(HttpServletRequest request) throws Exception {
    	String body = request.getReader().lines().collect(Collectors.joining());
    	log.info(" === request body is : [{}]", body);
    	String id = JsonCode.getValue(body, "$.id");
    	Preconditions.checkState(StringUtils.isNotBlank(id), "id不能为null");
    	
    	long userId = Long.parseLong(id);
    	return userService.deleteUser(userId);
    	
    }
    /**
     * 查询用户
     */
    @GetMapping("get")
    public Result<Object> getUser(@RequestParam("id")Long id) throws Exception {
    	
    	log.info(" === request params is : [id: {}]", id);
    	return userService.getUser(id);
    	
    }
    
    /**
     * 编辑用户
     */
    @PostMapping("update")
    public Result<Object> updateUser(HttpServletRequest request) throws Exception {
    	
    	String body = request.getReader().lines().collect(Collectors.joining());
    	log.info(" === request body is : [{}]", body);
    	String id = JsonCode.getValue(body, "$.id");
    	String username = JsonCode.getValue(body, "$.username");
    	String password = JsonCode.getValue(body, "$.password");
    	String roleId = JsonCode.getValue(body, "$.roleId");
    	Preconditions.checkState(!StringUtils.isAnyBlank(id, username, password, roleId), "请求参数有错误");
    	UserVo userVo = new UserVo();
    	userVo.setId(Long.parseLong(id));
    	userVo.setPassword(password);
    	userVo.setUsername(username);
    	userVo.setRoleId(Long.parseLong(roleId));
    	return userService.updateUser(userVo);
    	
    }
    

   
    
    


}
package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.enums.ResultCodeEnum.ILLEGAL_TOKEN;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.TOKEN_EXPIRED;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.po.RolePo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.security.JwtTokenUtil;
import com.boe.admin.uiadmin.security.User;
import com.google.common.collect.Maps;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    

    
    @Autowired
    private UserService userService;
    
   


    /**
     * {@link com.boe.admin.uiadmin.security.MyUserDetailsService #loadUserByUsername}
     */
    public Result<Object> login(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken( username, password );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername( username );
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Result.of(data, "登录成功", 200);
    }


	public Result<Object> info(String token) throws Exception {
		String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
        	return Result.of(null, ILLEGAL_TOKEN);
        }
        
        boolean tokenExpired = jwtTokenUtil.isTokenExpired(token);
        if(tokenExpired) {
        	return Result.of(null, TOKEN_EXPIRED);
        }
        
        LambdaQueryWrapper<UserPo> usernameQuery = Wrappers.<UserPo>lambdaQuery().eq(UserPo::getUsername, username);
        UserPo userPo = userService.getOne(usernameQuery, false);
        
        
        if (userPo == null) {
        	return Result.of(null, ILLEGAL_TOKEN);
        }
        
        User user = new User();
        user.setUsername(userPo.getUsername());
        user.setPassword(userPo.getPassword());
        
        
 
        if (! jwtTokenUtil.validateToken(token, user)) {
        	return Result.of(null, ILLEGAL_TOKEN);
        }
		
		
		List<RolePo> roleList = userService.selectRolesByUserId(userPo.getId());

		Map<String, Object> map = Maps.newHashMap();
		
		map.put("roles", roleList.stream().map(RolePo::getName).collect(Collectors.toList()));
		map.put("name", username);
		
		return Result.of(map, SUCCESS);
	}



}
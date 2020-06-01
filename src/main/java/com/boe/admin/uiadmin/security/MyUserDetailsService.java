package com.boe.admin.uiadmin.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.dao.UserRoleMapper;
import com.boe.admin.uiadmin.po.RolePo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.po.UserRolePo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 
 * Service 层需要实现 UserDetailsService 接口，该接口是根据用户名获取该用户的所有信息， 包括用户信息和权限点。
 *
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("=== [username:{}]", username);
		// 查数据库
		LambdaQueryWrapper<UserPo> userQuery = Wrappers.lambdaQuery();
		userQuery.eq(UserPo::getUsername, username);
		UserPo userPo = userMapper.selectOne(userQuery);

		if (null == userPo) {
			return null;
		}

		Long userId = userPo.getId();
		LambdaQueryWrapper<UserRolePo> userRoleQuery = Wrappers.lambdaQuery();
		userRoleQuery.eq(UserRolePo::getUserId, userId);
		List<UserRolePo> userRoleList = userRoleMapper.selectList(userRoleQuery);
		
		List<Role> roleList = Lists.newArrayList();
		
		for (UserRolePo userRolePo : userRoleList) {
			Long roleId = userRolePo.getRoleId();
			RolePo rolePo = roleMapper.selectById(roleId);
			Role role = new Role();
			role.setId(rolePo.getId());
			role.setName(rolePo.getName());
			roleList.add(role);
		}
		
		User user = new User();
		
		user.setId(userId);
		user.setUsername(userPo.getUsername());
		user.setPassword(userPo.getPassword());
		user.setAuthorities(roleList);
		
		return user;

	}

}

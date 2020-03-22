package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.PermissionMapper;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.dao.RolePermissionMapper;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.dao.UserRoleMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.RolePermissionPo;
import com.boe.admin.uiadmin.po.RolePo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.po.UserRolePo;
import com.boe.admin.uiadmin.vo.UserVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Service
public class UserService extends ServiceImpl<UserMapper, UserPo> implements IService<UserPo> {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 根据用户id查询用户所有权限
	 */
    public List<PermissionPo> selectPermissionsByUserId(Long userId) {
    	LambdaQueryWrapper<UserRolePo> lambdaQuery = Wrappers.lambdaQuery();
    	lambdaQuery.eq(UserRolePo::getUserId, userId);
    	List<UserRolePo> list = userRoleMapper.selectList(lambdaQuery);
    	List<PermissionPo> result = Lists.newArrayList();
    	for (UserRolePo userRolePo : list) {
			Long roleId = userRolePo.getRoleId();
			LambdaQueryWrapper<RolePermissionPo> roleIdQuery = Wrappers.lambdaQuery();
			roleIdQuery.eq(RolePermissionPo::getRoleId, roleId);
			List<RolePermissionPo> selectList = rolePermissionMapper.selectList(roleIdQuery);
			for (RolePermissionPo rolePermissionPo : selectList) {
				Long permissionId = rolePermissionPo.getPermissionId();
				PermissionPo permissionPo = permissionMapper.selectById(permissionId);
				result.add(permissionPo);
				
			}
			
		}
    	return result;
        
    }
    /**
     * 根据用户id查询用户所有角色
     */
    public List<RolePo> selectRolesByUserId(Long userId) {
    	LambdaQueryWrapper<UserRolePo> lambdaQuery = Wrappers.lambdaQuery();
    	lambdaQuery.eq(UserRolePo::getUserId, userId);
    	List<UserRolePo> list = userRoleMapper.selectList(lambdaQuery);
    	List<RolePo> result = Lists.newArrayList();
    	for (UserRolePo userRolePo : list) {
			Long roleId = userRolePo.getRoleId();
			RolePo rolePo = roleMapper.selectById(roleId);
			result.add(rolePo);
		}
        return result;
    }

    /**
     * 添加用户
     * 1. 校验用户名是否存在
     * 2. 校验角色是否存在
     * 3. 添加用户(密码加密) 
     * 4. 添加用户角色关系
     */
    @Transactional
	public Result<Object> addUser(@Valid UserVo userVo) {
   
    	String username = userVo.getUsername();
    	Long roleId = userVo.getRoleId();
    	//valid username if exists
    	LambdaQueryWrapper<UserPo> usernameQuery = Wrappers.lambdaQuery();
    	usernameQuery.eq(UserPo::getUsername, username);
    	UserPo selectOne = userMapper.selectOne(usernameQuery);
    	if(selectOne != null) {
    		return of(null, "用户名已近存在", 400);
    	}
    	//valid roleId if exists
    	RolePo rolePo = roleMapper.selectById(roleId);
    	if(rolePo == null) {
    		return of(null, "角色不存在", 400);
    	}
    	
    	
    	
    	// save user
    	UserPo userPo = new UserPo();
    	userPo.setUsername(username);
    	userPo.setPassword(DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes()));//密码加密存储
    	userPo.setBasePoFields();
    	userMapper.insert(userPo);
    	
    	
    	//save userrole relationship
    	Long userId = userPo.getId();
    	UserRolePo userRolePo = new UserRolePo();
    	userRolePo.setRoleId(roleId);
    	userRolePo.setUserId(userId);
    	userRolePo.setBasePoFields();
    	userRoleMapper.insert(userRolePo);
    	
    	return of(null, "用户添加成功", 200);
    	
    	
    	
	}
    /**
     * 查询用户列表
     */
	public Result<Object> listUsers(String username, Long currentPage, Long pageSize) throws Exception {
		LambdaQueryWrapper<UserPo> usernameQuery = Wrappers.lambdaQuery();
		if(StringUtils.isNotBlank(username)) {
			usernameQuery.like(UserPo::getUsername, username);
		}
		
		Page<UserPo> page = new Page<>(currentPage, pageSize);
		page.setOrders(Arrays.asList(OrderItem.asc("id")));
		page = userMapper.selectPage(page, usernameQuery);
		List<UserPo> records = page.getRecords();
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, Object>> list = Lists.newArrayList();
		for (UserPo userPo : records) {
			Long userId = userPo.getId();
			LambdaQueryWrapper<UserRolePo> userIdQuery = Wrappers.lambdaQuery();
			userIdQuery.eq(UserRolePo::getUserId, userId);
			UserRolePo userRolePo = userRoleMapper.selectOne(userIdQuery);
			Long roleId = userRolePo.getRoleId();
			RolePo rolePo = roleMapper.selectById(roleId);
			
			Map<String, Object> map = PropertyUtils.describe(userPo);
			map.put("roleName", rolePo.getName());
			map.put("alias", rolePo.getAlias());
			list.add(map);
		}
		
		data.put("total", page.getTotal());//总条数
		data.put("list", list);
		return of(data, "用户列表查询成功", 200);

		
	}

}
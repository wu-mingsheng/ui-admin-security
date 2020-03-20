package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.dao.UserRoleMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.RolePo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.po.UserRolePo;
import com.boe.admin.uiadmin.vo.UserVo;


@Service
public class UserService extends ServiceImpl<UserMapper, UserPo> implements IService<UserPo> {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper rolepaMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	

	/**
	 * 根据用户id查询用户所有权限
	 */
    public List<PermissionPo> selectPermissionsByUserId(Long userId) {
    	
        return userMapper.selectPermissionsByUserId(userId);
    }
    /**
     * 根据用户id查询用户所有角色
     */
    public List<RolePo> selectRolesByUserId(Long userId) {
    	
        return userMapper.selectRolesByUserId(userId);
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
    	RolePo rolePo = rolepaMapper.selectById(roleId);
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

}
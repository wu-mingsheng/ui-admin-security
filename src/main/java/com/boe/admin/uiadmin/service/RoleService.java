package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.dao.RolePermissionMapper;
import com.boe.admin.uiadmin.dao.UserRoleMapper;
import com.boe.admin.uiadmin.po.RolePermissionPo;
import com.boe.admin.uiadmin.po.RolePo;
import com.boe.admin.uiadmin.po.UserRolePo;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.utils.UserUtil;
import com.boe.admin.uiadmin.vo.RoleVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	public Result<Object> addRole(String name, String alias) {
		
		LambdaQueryWrapper<RolePo> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(RolePo::getName, name);
		RolePo selectOne = roleMapper.selectOne(queryWrapper);
		if(selectOne != null) {
			return of(null, "角色名称已经存在", 400);
		}
		
		RolePo rolePo = new RolePo();
		rolePo.setAlias(alias);
		rolePo.setName(name);
		rolePo.setBasePoFields();
		roleMapper.insert(rolePo);
		return of(null, "角色创建成功", 200);
		
		
	}

	public Result<Object> listAll() throws Exception {
		List<RolePo> list = roleMapper.selectList(null);
		List<Map<String, Object>> data = Lists.newArrayList();
		for (RolePo rolePo : list) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("id", rolePo.getId());
			map.put("name", rolePo.getName());
			map.put("description", rolePo.getAlias());
			data.add(map);
		}
		
		return of(data, SUCCESS);
	}

	/**
	 * 
	 * 删除角色
	 * 1. 删除角色基本信息
	 * 2. 删除用户角色关系表
	 * 3. 删除角色权限关系表
	 */
	@Transactional
	public Result<Object> deleteRole(Long id) throws Exception {
		//1. 删除角色基本信息
		roleMapper.deleteById(id);
		//2. 删除用户角色关系表
		userRoleMapper.delete(Wrappers.<UserRolePo>lambdaQuery().eq(UserRolePo::getRoleId, id));
		//3. 删除角色权限关系表
		rolePermissionMapper.delete(Wrappers.<RolePermissionPo>lambdaQuery().eq(RolePermissionPo::getRoleId, id));
		return of(null, SUCCESS);
	}

	
	/**
	 * 编辑角色,只能修改名称和描述(别名)
	 */
	public Result<Object> updateRole(RoleVo roleVo) throws Exception {
		RolePo rolePo = roleMapper.selectById(roleVo.getId());
		rolePo.setName(roleVo.getName());
		rolePo.setAlias(roleVo.getAlias());
		rolePo.setUpdateTime(DateUtil.now());
		rolePo.setUpdaterId(UserUtil.getCurrentUserId());
		roleMapper.updateById(rolePo);
		
		return of(null, "角色编辑成功", 200);
		
	}

}
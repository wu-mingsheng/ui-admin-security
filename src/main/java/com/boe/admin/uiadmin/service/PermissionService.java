package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.PermissionMapper;
import com.boe.admin.uiadmin.dao.RolePermissionMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.RolePermissionPo;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.utils.UserUtil;
import com.boe.admin.uiadmin.vo.PermissionVo;
import com.google.common.collect.Lists;


@Service
public class PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	
	public Result<Object> permissionTree() throws Exception {
		
		List<PermissionPo> list = getChildren(0L);
		return of(list, "权限列表获取成功", 200);
	}
	
	private List<PermissionPo> getChildren(Long pid) throws Exception {
		LambdaQueryWrapper<PermissionPo> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(PermissionPo::getPid, pid);
		List<PermissionPo> list = permissionMapper.selectList(queryWrapper);
		if(list != null && list.size() > 0) { // 存在子节点
			for (PermissionPo permissionPo : list) {
				Long currentId = permissionPo.getId();
				List<PermissionPo> children = getChildren(currentId);
				permissionPo.setChildren(children);
				
			}
			return list;
		}
		return Lists.newArrayList();
	}

	public Result<Object> rolePermission(Long roleId) throws Exception {
		LambdaQueryWrapper<RolePermissionPo> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(RolePermissionPo::getRoleId, roleId);
		List<RolePermissionPo> list = rolePermissionMapper.selectList(queryWrapper);
		Set<Long> permissionIds = list.stream().map(RolePermissionPo::getPermissionId).collect(Collectors.toSet());
		return of(permissionIds, SUCCESS);
	}

	@Transactional
	public Result<Object> addRolePermission(Long roleId, Set<Long> permisssionIdSet) throws Exception{
		//1. 删除旧数据
		LambdaQueryWrapper<RolePermissionPo> deleteQuery = Wrappers.lambdaQuery();
		deleteQuery.eq(RolePermissionPo::getRoleId, roleId);
		rolePermissionMapper.delete(deleteQuery);
		//2. 维护新的数据
		RolePermissionPo rolePermissionPo = new RolePermissionPo();
		rolePermissionPo.setBasePoFields();
		for (Long permissionId : permisssionIdSet) {
			rolePermissionPo.setRoleId(roleId);
			rolePermissionPo.setPermissionId(permissionId);
			rolePermissionMapper.insert(rolePermissionPo);
		}
		return of(null, SUCCESS);
	}

	
	
	public Result<Object> addPermission(@Valid PermissionVo permissionVo) throws Exception {
		String url = permissionVo.getUrl();
		LambdaQueryWrapper<PermissionPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(PermissionPo::getUrl, url);
		PermissionPo selectOne = permissionMapper.selectOne(lambdaQuery);
		if(selectOne != null) {
			return of(null, "url已近存在", 400);
		}
		PermissionPo permissionPo = new PermissionPo();
		PropertyUtils.copyProperties(permissionPo, permissionVo);
		permissionPo.setBasePoFields();
		permissionMapper.insert(permissionPo);
		return of(null, SUCCESS);
		
		
		
		
		
	}

	/**
	 * 删除权限
	 * 1. 删除权限基本信息
	 * 2. 删除角色权限关系
	 * 3. 删除子权限及子权限关系
	 */
	public Result<Void> deletePermission(Long permissionId) throws Exception {
		
		handleDel(permissionId);
		return of(null, "权限删除成功", 200);
	}
	
	private void handleDel(Long id) throws Exception {
		//删除当前节点的角色权限关系
		rolePermissionMapper.delete(Wrappers.<RolePermissionPo>lambdaQuery().eq(RolePermissionPo::getPermissionId, id));
		//删除当前节点
		permissionMapper.deleteById(id);
		//查找子节点
		LambdaQueryWrapper<PermissionPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(PermissionPo::getPid, id);
		List<PermissionPo> list = permissionMapper.selectList(lambdaQuery);
		for (PermissionPo permissionPo : list) {
			handleDel(permissionPo.getId());
		}
	}

	/**
	 * 编辑权限,只修改基本信息
	 */
	public Result<Void> updatePermission(@Valid PermissionVo permissionVo) {
		PermissionPo permissionPo = permissionMapper.selectById(permissionVo.getId());
		permissionPo.setName(permissionVo.getName());
		permissionPo.setDescription(permissionVo.getDescription());
		permissionPo.setUrl(permissionVo.getUrl());
		permissionPo.setUpdaterId(UserUtil.getCurrentUserId());
		permissionPo.setUpdateTime(DateUtil.now());
		permissionMapper.updateById(permissionPo);
		return of(null, "权限编辑成功", 200);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
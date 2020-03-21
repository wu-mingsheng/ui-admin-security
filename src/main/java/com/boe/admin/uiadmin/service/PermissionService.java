package com.boe.admin.uiadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.PermissionMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.google.common.collect.Lists;


@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionPo> implements IService<PermissionPo> {

	@Autowired
	private PermissionMapper permissionMapper;
	
	
	public Result<Object> permissionTree() throws Exception {
		
		List<PermissionPo> list = getChildren(0L);
		return Result.of(list, "权限列表获取成功", 200);
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
}
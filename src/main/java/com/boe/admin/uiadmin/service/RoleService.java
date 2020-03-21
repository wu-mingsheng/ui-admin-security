package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.po.RolePo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePo> implements IService<RolePo> {

	@Autowired
	private RoleMapper roleMapper;

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

}
package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.InstitutionMapper;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.enums.StateEnum;
import com.boe.admin.uiadmin.enums.SyncStateEnum;
import com.boe.admin.uiadmin.po.InstitutionPo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.utils.UserUtil;
import com.boe.admin.uiadmin.vo.InstitutionVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class InstitutionService {
	
	@Autowired
	private InstitutionMapper institutionMapper;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 添加机构
	 * 1. 机构名称不能重复
	 * 2. 添加机构
	 * 3. 同步crm(可以使用异步)
	 * 4. 更新同步时间和同步状态
	 */
	@Transactional
	public Result<Object> addInstitution(InstitutionVo institutionVo) throws Exception {
		//1. 判断机构名称是否存在
		InstitutionPo one = institutionMapper.selectOne(Wrappers.<InstitutionPo>lambdaQuery()
				.eq(InstitutionPo::getName, institutionVo.getName()));
		if(null != one) {
			return of(null, "机构名称已经存在", 400);
		}
		
		//2. 添加机构
		InstitutionPo institutionPo = new InstitutionPo();
		institutionPo.setName(institutionVo.getName());
		institutionPo.setCategory(institutionVo.getCategory());
		institutionPo.setAccountNum(institutionVo.getAccountNum());
		institutionPo.setBandWidth(institutionVo.getBandWidth());
		institutionPo.setDiskSpace(institutionVo.getDiskSpace());
		institutionPo.setBasePoFields();
		institutionPo.setState(StateEnum.valueOf(institutionVo.getState()));
		institutionPo.setSyncState(SyncStateEnum.NOT.value());
		institutionPo.setSyncTime(DateUtil.now());
		
		institutionMapper.insert(institutionPo);
		
		//3. 同步到crm
		//TODO
		
		//4. 跟新同步字段
		
		institutionPo.setSyncState(SyncStateEnum.HAS.value());
		institutionPo.setSyncTime(DateUtil.now());
		
		institutionMapper.updateById(institutionPo);
		
		return of(null, "机构添加成功", 200);
	}

	
	public Result<Object> list(InstitutionVo institutionVo) throws Exception {
		LambdaQueryWrapper<InstitutionPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.ne(InstitutionPo::getState, StateEnum.DELETED);//排除删除
		if(StringUtils.isNotBlank(institutionVo.getName())) {
			lambdaQuery.like(InstitutionPo::getName, institutionVo.getName());
		}
		if(StringUtils.isNotBlank(institutionVo.getCategory())) {
			lambdaQuery.eq(InstitutionPo::getCategory, institutionVo.getCategory());
		}
		if(StringUtils.isNotBlank(institutionVo.getState())) {
			lambdaQuery.eq(InstitutionPo::getState, StateEnum.valueOf(institutionVo.getState()));
		}
		Page<InstitutionPo> page = new Page<>(institutionVo.getPage(), institutionVo.getPageSize());
		page.setOrders(Arrays.asList(OrderItem.asc("id")));
		page = institutionMapper.selectPage(page, lambdaQuery);
		List<InstitutionPo> records = page.getRecords();
		List<Map<String, Object>> list = Lists.newArrayList();
		for (InstitutionPo institutionPo : records) {
			Map<String, Object> map = PropertyUtils.describe(institutionPo);
			Long updaterId = institutionPo.getUpdaterId();//操作人id
			UserPo userPo = userMapper.selectById(updaterId);
			map.put("updateName", userPo == null? "/" : userPo.getUsername());
			list.add(map);
			
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("total", page.getTotal());
		map.put("records", list);
		return of(map, "查询成功", 200);
	}


	/**
	 * 删除机构
	 * 1. 逻辑删除机构
	 * 2. 逻辑删除机构下的所有订单
	 * 3. 同步crm
	 * 4. 更新同步字段
	 * 
	 */
	public Result<Object> deleteInstitution(Long id) throws Exception {
		InstitutionPo institutionPo = institutionMapper.selectById(id);
		if(institutionPo == null) {
			return of(null, "机构不存在", 400);
		}
		institutionPo.setState(StateEnum.DELETED);
		institutionPo.setUpdaterId(UserUtil.getCurrentUserId());
		institutionPo.setUpdateTime(DateUtil.now());
		institutionMapper.updateById(institutionPo);
		return of(null, "删除成功", 200);
	}


	/**
	 * 停用机构
	 * 1. 停用机构
	 * 2. 同步crm
	 * 3. 更新同步字段
	 * 
	 */
	public Result<Object> stopInstitution(Long id) throws Exception {
		InstitutionPo institutionPo = institutionMapper.selectById(id);
		if(institutionPo == null) {
			return of(null, "机构不存在", 400);
		}
		institutionPo.setState(StateEnum.DISABLED);
		institutionPo.setUpdaterId(UserUtil.getCurrentUserId());
		institutionPo.setUpdateTime(DateUtil.now());
		institutionMapper.updateById(institutionPo);
		return of(null, "停用成功", 200);
	}


	@Transactional
	public Result<Object> syncInstitution(Long[] ids) {
		LocalDateTime now = DateUtil.now();
		Long updaterId = UserUtil.getCurrentUserId();
		for (Long id : ids) {
			InstitutionPo institutionPo = institutionMapper.selectById(id);
			institutionPo.setSyncState(SyncStateEnum.HAS.value());
			institutionPo.setSyncTime(now);
			institutionPo.setUpdateTime(now);
			institutionPo.setUpdaterId(updaterId);
			//调用crm接口
			institutionMapper.updateById(institutionPo);
		}
		return of(null, "同步成功", 200);
	}


	public Result<Object> getOne(Long id) throws Exception {
		InstitutionPo institutionPo = institutionMapper.selectById(id);
		return of(institutionPo, "获取成功", 200);
	}


	public Result<Object> updateInstitution(InstitutionVo institutionVo) throws Exception {
		LambdaQueryWrapper<InstitutionPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(InstitutionPo::getName, institutionVo.getName())
				   .ne(InstitutionPo::getId, institutionVo.getId());
		List<InstitutionPo> list = institutionMapper.selectList(lambdaQuery);
		if(list != null && list.size() > 0) {
			return of(null, "机构名称已近存在", 400);
		}
		InstitutionPo institutionPo = institutionMapper.selectById(institutionVo.getId());
		LocalDateTime now = DateUtil.now();
		institutionPo.setName(institutionVo.getName());
		institutionPo.setCategory(institutionVo.getCategory());
		institutionPo.setAccountNum(institutionVo.getAccountNum());
		institutionPo.setBandWidth(institutionVo.getBandWidth());
		institutionPo.setDiskSpace(institutionVo.getDiskSpace());
		institutionPo.setState(StateEnum.valueOf(institutionVo.getState()));
		institutionPo.setSyncState(SyncStateEnum.NOT.value());
		institutionPo.setSyncTime(now);
		institutionPo.setUpdateTime(now);
		institutionPo.setUpdaterId(UserUtil.getCurrentUserId());
		institutionMapper.updateById(institutionPo);
		//3. 同步到crm
		//TODO
		
		//4. 跟新同步字段
		
		institutionPo.setSyncState(SyncStateEnum.HAS.value());
		institutionPo.setSyncTime(DateUtil.now());
		
		institutionMapper.updateById(institutionPo);
		return of(null, "更新成功", 200);
	}

}

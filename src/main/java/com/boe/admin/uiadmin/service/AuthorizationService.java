package com.boe.admin.uiadmin.service;

import static com.boe.admin.uiadmin.common.Result.of;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.dao.AuthorizationMapper;
import com.boe.admin.uiadmin.dao.InstitutionMapper;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.enums.StateEnum;
import com.boe.admin.uiadmin.po.AuthorizationPo;
import com.boe.admin.uiadmin.po.InstitutionPo;
import com.boe.admin.uiadmin.po.UserPo;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.vo.AuthorizationVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class AuthorizationService {
	
	
	@Autowired
	private InstitutionMapper institutionMapper;
	
	@Autowired
	private AuthorizationMapper authorizationMapper;
	
	@Autowired
	private UserMapper userMapper;

	public Result<Object> list(AuthorizationVo authorizationVo) {
		
		
		LambdaQueryWrapper<InstitutionPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.ne(InstitutionPo::getState, StateEnum.DELETED);//排除删除
		if(StringUtils.isNotBlank(authorizationVo.getName())) {
			lambdaQuery.like(InstitutionPo::getName, authorizationVo.getName());
		}
		if(StringUtils.isNotBlank(authorizationVo.getState())) {
			lambdaQuery.eq(InstitutionPo::getState, StateEnum.valueOf(authorizationVo.getState()));
		}
		Page<InstitutionPo> page = new Page<>(authorizationVo.getPage(), authorizationVo.getPageSize());
		page.setOrders(Arrays.asList(OrderItem.asc("id")));
		page = institutionMapper.selectPage(page, lambdaQuery);
		Map<String, Object> data = Maps.newHashMap();
		long total = page.getTotal();
		List<InstitutionPo> records = page.getRecords();
		List<Map<String, Object>> list = Lists.newArrayList();
		for (InstitutionPo institutionPo : records) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", institutionPo.getId());
			map.put("name", institutionPo.getName());
			map.put("state", institutionPo.getState().toString());
			map.put("syncState", institutionPo.getSyncState());
			map.put("syncTime", institutionPo.getSyncTime());
			map.put("updateTime", institutionPo.getUpdateTime());
			Map<String, Object> computeAuthorizateInfo = computeAuthorizateInfo(institutionPo.getId());
			map.put("authorizedNum", computeAuthorizateInfo.get("authorizedNum"));// 授权数量
			map.put("expirationDate", computeAuthorizateInfo.get("expirationDate"));// 有效期
			map.put("startTime", computeAuthorizateInfo.get("startTime"));// 启用时间
			map.put("endTime", computeAuthorizateInfo.get("endTime"));// 停用时间
			
			list.add(map);
		}
		data.put("total", total);
		data.put("list", list);
		return of(data, SUCCESS);
		
	}
	
	private Map<String, Object> computeAuthorizateInfo(Long id) {
		LambdaQueryWrapper<AuthorizationPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(AuthorizationPo::getInstitutionId, id).ne(AuthorizationPo::getState, StateEnum.DELETED);
		List<AuthorizationPo> list = authorizationMapper.selectList(lambdaQuery);
		int num = 0;
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		LocalDateTime now = LocalDateTime.now();
		for (AuthorizationPo authorizationPo : list) {
			LocalDateTime curStartTime = authorizationPo.getStartTime();
			LocalDateTime curEndTime = authorizationPo.getEndTime();
			if(now.isBefore(curEndTime) && now.isAfter(curStartTime)) {//当前生效订单
				Integer authorizeNum = authorizationPo.getAuthorizeNum();
				num = num + authorizeNum;
				if(startTime == null) {
					startTime = curStartTime;
					endTime = curEndTime;
				}else {
					if(curStartTime.isBefore(startTime)) {
						startTime = curStartTime;
					}
					if(curEndTime.isAfter(endTime)) {
						endTime = curEndTime;
					}
				}
				
			}
			
		}
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("authorizedNum", num);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		if(startTime != null && endTime != null) {
			map.put("expirationDate", Duration.between(startTime, endTime).toDays() + "天");
			
		}else {
			map.put("expirationDate", null);
		}
		
		return map;		
		
		
		
		
		
	}

	public Result<Object> addAuthorization(AuthorizationVo authorizationVo) throws Exception {
		Long institutionId = authorizationVo.getInstitutionId();
		InstitutionPo institutionPo = institutionMapper.selectById(institutionId);
		if(null == institutionPo) {
			return of(null, "机构不存在", 400);
		}
		AuthorizationPo authorizationPo = new AuthorizationPo();
		authorizationPo.setAuthorizeNum(authorizationVo.getAuthorizeNum());
		authorizationPo.setInstitutionId(authorizationVo.getInstitutionId());
		authorizationPo.setStartTime(authorizationVo.getStartTime());
		authorizationPo.setEndTime(authorizationVo.getEndTime());
		authorizationPo.setBasePoFields();
        authorizationMapper.insert(authorizationPo);
        
        return of(null, SUCCESS);
		
	}

	public Result<Object> orders(Long id) throws Exception {
		LambdaQueryWrapper<AuthorizationPo> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(AuthorizationPo::getInstitutionId, id)
				   .ne(AuthorizationPo::getState, StateEnum.DELETED);
		
		List<AuthorizationPo> all = authorizationMapper.selectList(lambdaQuery);
		List<Object> list = Lists.newArrayList();
		LocalDateTime now = LocalDateTime.now();
		Integer totalNum = 0; //总授权
		Integer availableNum = 0; //当前可用授权
		for (AuthorizationPo authorizationPo : all) {
			Map<Object,Object> map = Maps.newHashMap();
			map.put("id", authorizationPo.getId());
			map.put("num", authorizationPo.getAuthorizeNum());
			map.put("expireDate", Duration.between(authorizationPo.getStartTime(),authorizationPo.getEndTime()).toDays() + "天");//有效期
			map.put("authorizationDate", authorizationPo.getUpdateTime().toLocalDate());
			map.put("effectiveDate", authorizationPo.getStartTime().toLocalDate());
			map.put("failureDate", authorizationPo.getEndTime().toLocalDate());
			
			
			if(now.isBefore(authorizationPo.getStartTime())) {
				map.put("state", "未开始");
			}else if(now.isAfter(authorizationPo.getEndTime())) {
				map.put("state", "已结束");
			}else {
				map.put("state", "进行中");
				availableNum = availableNum + authorizationPo.getAuthorizeNum();
			}
			
			UserPo userPo = userMapper.selectById(authorizationPo.getUpdaterId());
			map.put("updaterName", userPo == null ? "N/A" : userPo.getUsername());
			totalNum = totalNum + authorizationPo.getAuthorizeNum();
			
			list.add(map);
		}
		
		Map<Object, Object> data = Maps.newHashMap();
		data.put("list", list);
		data.put("totalNum", totalNum);
		data.put("availableNum", availableNum);
		
		return of(data, SUCCESS);
	}



}

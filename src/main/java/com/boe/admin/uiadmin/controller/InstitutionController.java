package com.boe.admin.uiadmin.controller;

import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.enums.ResultCodeEnum;
import com.boe.admin.uiadmin.enums.StateEnum;
import com.boe.admin.uiadmin.po.InstitutionPo;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.boe.admin.uiadmin.vo.InstitutionListVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("institution")
@Validated
public class InstitutionController {
	
	@PostMapping("add")
	public Result<Object> addInstitution(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info(" === request body is : [{}]", body);
		return Result.of(null, "添加机构成功", 200);
	}
	
	@PostMapping("update")
	public Result<Object> updateInstitution(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info(" === request body is : [{}]", body);
		return Result.of(null, "编辑机构成功", 200);
	}
	
	
	@GetMapping("getDetail")
	public Result<Object> getDetail(@RequestParam("id") Integer id) throws Exception {
		log.info(" === request param is : [ id = {}]", id);
		LocalDateTime now = DateUtil.now();
		InstitutionPo institutionPo = InstitutionPo.builder()
				.id(1)
				.createrId(1)
				.updaterId(1)
				.createTime(now)
				.updateTime(now)
				.category("CMS")
				.name("小米优品")
				.state(StateEnum.ENABLED)
				.syncState(1)
				.syncTime(now)
				.build();
		
		return Result.of(institutionPo, SUCCESS);
	}
	
	/** 机构列表 */
	@GetMapping("list")
	public Result<Object> list(@Valid InstitutionListVo queryVo) throws Exception {
		log.info(" === method params is : [{}]", queryVo);
		Map<String,Object> map = Maps.newHashMap();
		List<Map<String,String>> list = Lists.newArrayList();
		
		LocalDateTime now = DateUtil.now();
		InstitutionPo institutionPo = InstitutionPo.builder()
			.id(1)
			.createrId(1)
			.updaterId(1)
			.createTime(now)
			.updateTime(now)
			.category("CMS")
			.name("小米优品")
			.state(StateEnum.ENABLED)
			.syncState(1)
			.syncTime(now)
			.build();
		
		Map<String, String> describe = BeanUtils.describe(institutionPo);
		
		describe.put("updateName", "吴明升");
		
		list.add(describe);
		map.put("total", 1);
		map.put("list", list);
		return Result.of(map, "机构列表获取成功", 200);
	}
	
	
	

}

package com.boe.admin.uiadmin.controller;

import static com.boe.admin.uiadmin.common.Result.of;
import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("authorization")
@Slf4j
@Validated
public class AuthorizationController {
	
	 private Result<Object> ok(Object obj){
	    	
	    	return of(obj, SUCCESS);
	    }
	 @GetMapping("orders")
	 public Result<Object> orders(HttpServletRequest request) throws Exception{
		 Map<String, String[]> parameterMap = request.getParameterMap();
		 log.info("{}", parameterMap);
		 return ok(null);
	 }
	 
	 @GetMapping("list")
	 public Result<Object> list(HttpServletRequest request) throws Exception {
		 Map<String, String[]> parameterMap = request.getParameterMap();
		 log.info("{}", parameterMap);
		 Map<String, Object> data = Maps.newHashMap();
		 List<Object> list = Lists.newArrayList();
		 
		 for (int i = 0; i < 10; i++) {
			HashMap<Object,Object> newHashMap = Maps.newHashMap();
			LocalDateTime now = DateUtil.now();
			newHashMap.put("id", i);
			newHashMap.put("name", "家乐福");
			newHashMap.put("state", "ENABLED");
			newHashMap.put("authorizedNum", 100);
			newHashMap.put("expirationDate", "18个月");
			newHashMap.put("startTime", now);
			newHashMap.put("endTime", now);
			newHashMap.put("syncState", 1);
			newHashMap.put("updateTime", now);
			newHashMap.put("syncTime", now);
		
			
			
			list.add(newHashMap);
		}
		 
		 data.put("list", list);
		 data.put("total", 140);
		 
		 return ok(data);
	 }
}

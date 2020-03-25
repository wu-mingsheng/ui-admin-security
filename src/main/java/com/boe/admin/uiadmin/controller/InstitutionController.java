package com.boe.admin.uiadmin.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.service.InstitutionService;
import com.boe.admin.uiadmin.valid.group.AddInstitutionGroup;
import com.boe.admin.uiadmin.valid.group.ListInistitutionGroup;
import com.boe.admin.uiadmin.valid.group.UpdateInstitutionGroup;
import com.boe.admin.uiadmin.vo.InstitutionVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("institution")
public class InstitutionController {
	
	
	@Autowired
	private InstitutionService institutionService;
	
	@GetMapping("getEnabledInstitutions")
	public Result<Object> getEnabledInstitutions() throws Exception {
		return institutionService.getEnabledInstitutions();
	}
	
	@PutMapping("sync")
	public Result<Object> syncInstitution(@RequestBody Long[] ids) throws Exception {
		log.info(" === request body is : [{}]", Arrays.stream(ids).map(String::valueOf).collect(Collectors.joining(",")));
		return institutionService.syncInstitution(ids);
	}
	
	@DeleteMapping("delete/{id}")
	public Result<Object> deleteInstitution(@PathVariable("id") Long id) throws Exception {
		log.info(" ==== request param is : [id : {}]", id);
		return institutionService.deleteInstitution(id);
	}
	
	@PutMapping("stop/{id}")
	public Result<Object> stopInstitution(@PathVariable("id") Long id) throws Exception {
		log.info(" ==== request param is : [id : {}]", id);
		return institutionService.stopInstitution(id);
	}
	
	@PostMapping("add")
	public Result<Object> addInstitution(@RequestBody @Validated(AddInstitutionGroup.class) InstitutionVo institutionVo)
			throws Exception {
		
		log.info(" === request body is : [{}]", institutionVo);
		return institutionService.addInstitution(institutionVo);
	}
	
	@PostMapping("update")
	public Result<Object> updateInstitution(@RequestBody @Validated(UpdateInstitutionGroup.class) InstitutionVo institutionVo) throws Exception {
		return institutionService.updateInstitution(institutionVo);
	}
	
	
	@GetMapping("getDetail")
	public Result<Object> getDetail(@RequestParam("id") Long id) throws Exception {
		log.info(" === request param is : [ id = {}]", id);

		return institutionService.getOne(id);
	}
	
	/** 机构列表 */
	@GetMapping("list")
	public Result<Object> list(@Validated(ListInistitutionGroup.class) InstitutionVo institutionVo) throws Exception {
		log.info(" === method params is : [{}]", institutionVo);
		return institutionService.list(institutionVo);
		
	}
	
	
	

}

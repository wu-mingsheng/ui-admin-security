package com.boe.admin.uiadmin.controller;

import static com.boe.admin.uiadmin.enums.ResultCodeEnum.SUCCESS;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boe.admin.uiadmin.common.Result;
import com.boe.admin.uiadmin.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.miludeer.jsoncode.JsonCode;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("file")
@Valid
public class FileController {
	
	
	@GetMapping("deleteBook")
	public Result<Object> deleteBook(@RequestParam("id") Integer id) throws Exception{
		return Result.of(null, "删除成功", 200);
	}
	
	@GetMapping("getTableList")
	public Result<Object> getTableList(HttpServletRequest request, 
			@RequestParam(value = "page", defaultValue = "1")Integer page, 
			@RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) throws Exception {
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
		   String name =  names.nextElement();
		   String value = request.getParameter(name);
		   log.info(name+"---"+value);
		}
		Map<String, Object> data = Maps.newHashMap();
		List<Object> list = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			Map<String,Object> map = Maps.newHashMap();
			map.put("id", i + 100);
			map.put("title", "title" + i);
			map.put("author", "wms" + i);
			map.put("publisher", "hellojwt" + i);
			map.put("category", "java" + i);
			map.put("language", "zh-cn" + i);
			map.put("filename", "");
			map.put("createTime", DateUtil.now());
			map.put("cover", "https://img.zcool.cn/community/014d7a5d7ce639a801211d533a604c.jpg@1280w_1l_2o_100sh.jpg");
			list.add(map);
		}
		
		data.put("list", list);
		data.put("page", page);
		data.put("pageSize", pageSize);
		data.put("count", 120);
		return Result.of(data, "获取图书列表成功", 200);
		
	}
	
	
	@GetMapping("getCategory")
	public Result<Object> getCategory(){
	    List<Map<String, Object>> data = Lists.newArrayList();
	    for (int i = 0; i < 5; i++) {
	    	Map<String,Object> map = Maps.newHashMap();
	    	map.put("label", "label"+ i);
	    	
	    	map.put("num", 100 + i);
	    	data.add(map);
		}
		
		return Result.of(data, SUCCESS);
	}
	@GetMapping("getBook")
	public Result<Object> getBook(@RequestParam("fileName")String fileName){
		 Map<String,Object> map = Maps.newHashMap();
	     map.put("fileName", fileName);
	     map.put("path", "path");
	     map.put("filePath", "filePath");
	     map.put("unzipPath", "unzipPath");
	     map.put("url", "url");
	     map.put("title", "title");
	     map.put("author", "author");
	     map.put("language", "zh-CN");
	     map.put("rootFile", "/home/user");
	     map.put("publisher", "publisher");
	     List<Object> list = Lists.newArrayList();
	     for (int i = 0; i < 4; i++) {
	    	 Map<String, Object> m = Maps.newHashMap();
	    	 m.put("label", "目录" + i);
	    	 m.put("www", "https://www.baidu.com");
	    	
	    	 if( i == 1) {
	    		 List<Map<String, Object>> list2 = Lists.newArrayList();
	    		 Map<String,Object> map2 = Maps.newHashMap();
	    		 map2.put("label", "二级目录");
	    		 list2.add(map2);
	    		 m.put("children", list2);
	    	 }
	    	 list.add(m);
		}
	     map.put("capterTree", list);
	     map.put("contents", list);
	     map.put("cover", "https://img.zcool.cn/community/014d7a5d7ce639a801211d533a604c.jpg@1280w_1l_2o_100sh.jpg");
	     map.put("coverPath", "coverPath");
	     map.put("category", "category");
	     map.put("categoryText", "categoryText");
	     return Result.of(map, SUCCESS);
		
		
	}
	
	
	@PostMapping("/upload")
	public Result<Object> singleFileUpload(@RequestParam("file") MultipartFile file) {
		 if (file.isEmpty()) {
			 return Result.of(null, "上传失败，请选择文件", 400);
	     }

	     String fileName = file.getOriginalFilename();
	     log.info("=== file upload [filename:{}]", fileName);
	     Map<String,Object> map = Maps.newHashMap();
	     map.put("fileName", fileName);
	     map.put("path", "path");
	     map.put("filePath", "filePath");
	     map.put("unzipPath", "unzipPath");
	     map.put("url", "url");
	     map.put("title", "title");
	     map.put("author", "author");
	     map.put("language", "zh-CN");
	     map.put("rootFile", "/home/user");
	     map.put("publisher", "publisher");
	     List<Object> list = Lists.newArrayList();
	     for (int i = 0; i < 4; i++) {
	    	 Map<String, Object> m = Maps.newHashMap();
	    	 m.put("label", "目录" + i);
	    	 m.put("www", "https://www.baidu.com");
	    	
	    	 if( i == 1) {
	    		 List<Map<String, Object>> list2 = Lists.newArrayList();
	    		 Map<String,Object> map2 = Maps.newHashMap();
	    		 map2.put("label", "二级目录");
	    		 list2.add(map2);
	    		 m.put("children", list2);
	    	 }
	    	 list.add(m);
		}
	     map.put("capterTree", list);
	     map.put("contents", list);
	     map.put("cover", "https://img.zcool.cn/community/014d7a5d7ce639a801211d533a604c.jpg@1280w_1l_2o_100sh.jpg");
	     map.put("coverPath", "coverPath");
	     map.put("category", "category");
	     map.put("categoryText", "categoryText");
	     return Result.of(map, SUCCESS);
	}
	
	@PostMapping("submitPostForm")
	public Result<Object> submitPostForm(HttpServletRequest request) throws Exception {
		String body = request.getReader().lines().collect(Collectors.joining()); 
		log.info("=== requestbody is : [{}]", body);
		String author = JsonCode.getValue(body, "$.author");
		String number = JsonCode.getValue(body, "$.number");
		String bool = JsonCode.getValue(body, "$.bool");
		log.info("=== parse json :[author:{}, number:{}, bool:{}] ", author, number, bool);
		return Result.of(null, SUCCESS);
	}

}

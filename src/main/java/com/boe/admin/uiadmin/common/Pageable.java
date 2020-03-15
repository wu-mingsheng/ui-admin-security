package com.boe.admin.uiadmin.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pageable<T> {
	
	private Integer pageIndex;
	
	private Integer pageCount;
	
	private Integer totalCount;
	
	private List<T> list;
	
	


	

}
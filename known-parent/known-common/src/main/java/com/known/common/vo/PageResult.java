package com.known.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T>{
	private Page page;
	
	private List<T> list;
	
	public PageResult(Page page, List<T> list) {
		this.page = page;
		this.list = list;
	}
}

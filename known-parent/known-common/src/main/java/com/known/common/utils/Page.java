package com.known.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page {

	private int start = 0;
	private int end;
	private int pageNum=1;//当前页
	private int count;//数据总数
	private int pageSize;//一页多少条数据
	private int pageTotal;//总共多少页
	
	public int getPageTotal() {
		return count%pageSize==0? count/pageSize:count/pageSize+1;
	}

	public Page(int pageNum, int count, int pageSize) {
		this.pageNum = pageNum;
		this.count = count;
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (pageNum - 1) * pageSize;
	}

}

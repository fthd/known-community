package com.known.service;

import com.known.common.model.Attachment;
import com.known.common.model.Blog;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.BlogQuery;

import java.util.List;

public interface BlogService {
	
	PageResult<Blog> findBlogByPage(BlogQuery blogQuery);
	
	Blog getBlog(Integer blogId);
	
	Blog showBlog(Integer blogId) throws BussinessException;
	
	void addBlog(Blog blog, Attachment attachment) throws BussinessException;
	
	void deleteBlog(Integer blogId) throws BussinessException;
	
	void modifyBlog(Blog blog, Attachment attachment)throws BussinessException;
	
	List<Blog> findBlogList();
	
	void deleteBatch(Integer[] blogIds)throws BussinessException;
}

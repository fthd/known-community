package com.known.service;

import com.known.common.model.BlogCategory;
import com.known.exception.BussinessException;

import java.util.List;

public interface BlogCategoryService {
	
	void addBlogCategory(BlogCategory blogCategory) throws BussinessException;
	
	List<BlogCategory> findBlogCategoryList(Integer userId);
	
	void deleteBlogCategory(Integer categoryId)throws BussinessException;
	
	BlogCategory getBlogCategory(Integer categoryId);
	
	void saveOrUpdate(BlogCategory blogCategory)throws BussinessException;
	
}

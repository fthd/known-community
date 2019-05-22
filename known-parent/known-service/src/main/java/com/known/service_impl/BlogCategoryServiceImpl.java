package com.known.service_impl;


import com.known.common.enums.TextLengthEnum;
import com.known.common.model.BlogCategory;
import com.known.common.model.User;
import com.known.common.utils.StringUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.BlogCategoryMapper;
import com.known.manager.query.BlogCategoryQuery;
import com.known.service.BlogCategoryService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {
	
	@Autowired
	private BlogCategoryMapper<BlogCategory, BlogCategoryQuery> blogCategoryMapper;
	
	@Autowired
	private UserService userService;

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void addBlogCategory(BlogCategory blogCategory)
			throws BussinessException {
		User user = this.userService.findUserByUserid(blogCategory.getUserId());
		if(user == null || StringUtil.isEmpty(blogCategory.getName()) || blogCategory.getName().length() >
			TextLengthEnum.TEXT_50_LENGTH.getLength() || !StringUtil.isNumber(blogCategory.getRank().toString())
				){
			throw new BussinessException("参数不合法");
		}
		blogCategory.setName(HtmlUtils.htmlUnescape(blogCategory.getName()));
		this.blogCategoryMapper.insert(blogCategory);				
	}

	public List<BlogCategory> findBlogCategoryList(Integer userId){
		BlogCategoryQuery blogCategoryQuery = new BlogCategoryQuery();
		blogCategoryQuery.setUserId(userId);
		List<BlogCategory> list = this.blogCategoryMapper.selectList(blogCategoryQuery);
		return list;
	}
	
	public BlogCategory getBlogCategory(Integer categoryId) {
		BlogCategoryQuery blogCategoryQuery = new BlogCategoryQuery();
		blogCategoryQuery.setCategoryId(categoryId);
		List<BlogCategory> list = this.blogCategoryMapper.selectList(blogCategoryQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	public void deleteBlogCategory(Integer categoryId)
			throws BussinessException {
		if(getBlogCategory(categoryId) == null){
			throw new BussinessException("分类不存在或已删除");
		}
		this.blogCategoryMapper.delete(categoryId);
	}
	
	public void saveOrUpdate(BlogCategory blogCategory)throws BussinessException{
		if(blogCategory.getCategoryId() != null){
			this.blogCategoryMapper.update(blogCategory);
		}
		else{
			this.addBlogCategory(blogCategory);
		}
		
	}
	
	
}

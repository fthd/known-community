package com.known.service_impl;

import com.known.cache.CategoryCache;
import com.known.common.model.Category;
import com.known.common.model.SysRes;
import com.known.common.utils.StringUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.CategoryMapper;
import com.known.manager.query.CategoryQuery;
import com.known.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper<Category, CategoryQuery> categoryMapper;
	
	@Autowired
	private CategoryCache categoryCache;

	public List<Category> findCategoryList(CategoryQuery categoryQuery) {
		List<Category> list =  categoryMapper.selectList(categoryQuery);
		list = getChildren(list, 0);
		return list;
	}
	
	public static List<Category> getChildren(List<Category> categories, int id){
		List<Category> children = new ArrayList<>();
		for(Category category : categories){
			if(category.getPid() == id){
				category.setChildren(getChildren(categories, category.getCategoryId()));
				children.add(category);
			}
		}
		return children;
	}

	public List<Category> findCategory4TopicCount(CategoryQuery categoryQuery) {
		List<Category> list =  categoryMapper.selectCategory4TopicCount(categoryQuery);
		list = getChildren(list, 0);
		return list;
	}

	public Category findCategoryBypCategoryId(Integer pCategoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			if(category.getCategoryId() == pCategoryId){
				return category;
			}
		}
		return null;
	}

	public Category findCategoryByCategoryId(Integer categoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			List<Category> children = category.getChildren();
			for(Category c : children){
				if(c.getCategoryId() == categoryId){
					return category;
				}
			}
		}
		return null;
	}

	public Category findSingleCategoryByCategoryId(Integer categoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			List<Category> children = category.getChildren();
			for(Category c : children){
				if(c.getCategoryId() == categoryId){
					return c;
				}
			}
		}
		return null;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void deleteCategory(Integer[] ids) throws BussinessException {
		if(ids == null || ids.length == 0){
			throw new BussinessException("参数错误");
		}
		// 上两级目录
		categoryMapper.deletePermission(ids);
		// 下一级目录
		categoryMapper.deleteIds(ids);
	}

	@Override
	public void addCategory(Category category) throws BussinessException {
		if(StringUtil.isEmpty(category.getName()) || category.getPid() == null
				|| StringUtil.isEmpty(category.getDesc())
				|| category.getRank() == null) {
			throw new BussinessException("参数错误");
		}

		categoryMapper.insert(category);
	}


	@Override
	public void updateCategory(Category category) throws BussinessException {
		if(category.getCategoryId() == null|| StringUtil.isEmpty(category.getName())
				|| category.getRank() == null)  {
			throw new BussinessException("参数错误");
		}

		categoryMapper.update(category);
	}

}

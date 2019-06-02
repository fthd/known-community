package com.known.service_impl;

import com.known.cache.CategoryCache;
import com.known.common.model.Category;
import com.known.common.utils.StringUtil;
import com.known.common.utils.UUIDUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.CategoryMapper;
import com.known.manager.query.CategoryQuery;
import com.known.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper<Category, CategoryQuery> categoryMapper;
	
	@Autowired
	private CategoryCache categoryCache;

	public List<Category> findCategoryList(CategoryQuery categoryQuery, boolean isNeedChild) {
		List<Category> list =  categoryMapper.selectList(categoryQuery);
		if(isNeedChild) {
			list = getChildren(list, null);
			// 要么是父节点，要么是有子节点的父节点才会添加
			list = list.parallelStream().filter(r -> (r.getPid() == null || r.getChildren().size() > 0)).collect(Collectors.toList());
		}
		return list;
	}
	
	public static List<Category> getChildren(List<Category> categories, String pid){
		List<Category> children = new ArrayList<>();
		for(Category category : categories){
			if(pid == null || pid.equals(category.getPid())){
				category.setChildren(getChildren(categories, category.getCategoryId()));
				children.add(category);
			}
		}
		return children;
	}

	public List<Category> findCategory4TopicCount(CategoryQuery categoryQuery) {
		List<Category> list =  categoryMapper.selectCategory4TopicCount(categoryQuery);
		list = getChildren(list, null);
		// 要么是父节点，要么是有子节点的父节点才会添加
		list = list.parallelStream().filter(r -> (r.getPid() == null || r.getChildren().size() > 0)).collect(Collectors.toList());
		return list;
	}

	public Category findCategoryBypCategoryId(String pCategoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			if(category.getCategoryId().equals(pCategoryId)){
				return category;
			}
		}
		return null;
	}

	public Category findCategoryByCategoryId(String categoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			List<Category> children = category.getChildren();
			for(Category c : children){
				if(c.getCategoryId() .equals(categoryId)){
					return category;
				}
			}
		}
		return null;
	}

	public Category findSingleCategoryByCategoryId(String categoryId) {
		List<Category> bbCategories = categoryCache.getTopicCategories();
		for(Category category : bbCategories){
			List<Category> children = category.getChildren();
			for(Category c : children){
				if(c.getCategoryId().equals(categoryId)){
					return c;
				}
			}
		}
		return null;
	}

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void deleteCategory(String[] ids) throws BussinessException {
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
		if(StringUtil.isEmpty(category.getName()) || StringUtil.isEmpty(category.getDesc())
				|| category.getRank() == null) {
			throw new BussinessException("参数错误");
		}
		category.setCategoryId(UUIDUtil.getUUID());
		category.setPid(StringUtil.isEmpty(category.getPid()) ? null : category.getPid());
		categoryMapper.insert(category);
	}


	@Override
	public void updateCategory(Category category) throws BussinessException {
		if(category.getCategoryId() == null|| StringUtil.isEmpty(category.getName())
				|| category.getRank() == null)  {
			throw new BussinessException("参数错误");
		}
		category.setPid(StringUtil.isEmpty(category.getPid()) ? null : category.getPid());
		categoryMapper.update(category);
	}

}

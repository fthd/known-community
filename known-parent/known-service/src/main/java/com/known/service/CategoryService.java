package com.known.service;


import com.known.common.model.Category;
import com.known.exception.BussinessException;
import com.known.manager.query.CategoryQuery;

import java.util.List;

public interface CategoryService {
	
	List<Category> findCategoryList(CategoryQuery categoryQuery, boolean isNeedChild);
	
	List<Category> findCategory4TopicCount(CategoryQuery categoryQuery);
	
	Category findCategoryBypCategoryId(String pCategoryId);
	
	Category findCategoryByCategoryId(String categoryId);
	
	Category findSingleCategoryByCategoryId(String categoryId);

	void deleteCategory(String[] ids)throws BussinessException;

	void addCategory(Category Category)throws BussinessException;

	void updateCategory(Category Category) throws BussinessException;
}

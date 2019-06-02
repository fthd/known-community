package com.known.cache;

import com.known.common.model.Category;
import com.known.common.utils.Constants;
import com.known.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载分类到缓存中
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-01 16:24
 */
@Component
public class CategoryCache {
	
	@Autowired
	private CategoryService categoryService;	
	private static Map<String, List<Category>> categoryCache;
	private static Map<String, Category> singleCategoryCache;
	
	static{
		categoryCache = new HashMap<>();
		singleCategoryCache = new HashMap<>();
		categoryCache.put(Constants.Cache_Key_Topic_Category, new ArrayList<>());
		categoryCache.put(Constants.Cache_Key_Knowledge_Category, new ArrayList<>());
		categoryCache.put(Constants.Cache_Key_Ask_Category, new ArrayList<>());
	}
	
	public void filterChildren(Category c, String show){
		List<Category> filterChildren = new ArrayList<>();
		List<Category> children = c.getChildren();
		for(Category category : children){
			if(show.equals(Constants.Cache_Key_Topic_Category) && Constants.Y.equals(category.getShowInTopic())){
				filterChildren.add(category);
			}
			if(show.equals(Constants.Cache_Key_Ask_Category) && Constants.Y.equals(category.getShowInAsk())){
				filterChildren.add(category);
			}
			if(show.equals(Constants.Cache_Key_Knowledge_Category) && Constants.Y.equals(category.getShowInKnowledge())){
				filterChildren.add(category);
			}
			singleCategoryCache.put(Constants.Cache_Key_Category+ category.getCategoryId(), category);
		}
		c.setChildren(filterChildren);
	}

	/**
	 * 刷新分类缓存
	 */
	public void refreshCategoryCache(){
		List<Category> list = categoryService.findCategoryList(null, true);
		for(Category category : list){
			if(Constants.Y.equals(category.getShowInTopic())){
				categoryCache.get(Constants.Cache_Key_Topic_Category).add(category);
				filterChildren(category, Constants.Cache_Key_Topic_Category);
			}
			if(Constants.Y.equals(category.getShowInKnowledge())){
				categoryCache.get(Constants.Cache_Key_Knowledge_Category).add(category);
				filterChildren(category, Constants.Cache_Key_Knowledge_Category);
			}
			if(Constants.Y.equals(category.getShowInAsk())){
				categoryCache.get(Constants.Cache_Key_Ask_Category).add(category);
				filterChildren(category, Constants.Cache_Key_Ask_Category);
			}
			singleCategoryCache.put(Constants.Cache_Key_Category + category.getCategoryId(), category);
		}
	}
	
	public List<Category> getTopicCategories(){
		return categoryCache.get(Constants.Cache_Key_Topic_Category);
	}
	public List<Category> getKnowledgeCategories(){
		return categoryCache.get(Constants.Cache_Key_Knowledge_Category);
	}
	public static Category getCategoryById(String categoryId){
		return singleCategoryCache.get(Constants.Cache_Key_Category + categoryId);
	}
}

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

@Component
public class CategoryCache {
	
	@Autowired
	private CategoryService categoryService;
	
	private static Map<String, List<Category>> categoryCache;
	private static Map<String, Category> singleCategoryCache;
	
	static{
		categoryCache = new HashMap<>();
		singleCategoryCache = new HashMap<>();
		categoryCache.put(Constants.CACHE_KEY_BBS_CATEGORY, new ArrayList<>());
		categoryCache.put(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY, new ArrayList<>());
		categoryCache.put(Constants.CACHE_KEY_ASK_CATEGORY, new ArrayList<>());
		categoryCache.put(Constants.CACHE_KEY_EXAM_CATEGORY, new ArrayList<>());
	}
	
	public void filterChildren(Category c, String show){
		List<Category> filterChildren = new ArrayList<>();
		List<Category> children = c.getChildren();
		for(Category category : children){
			if(show.equals(Constants.CACHE_KEY_BBS_CATEGORY) && Constants.Y.equals(category.getShowInBbs())){
				filterChildren.add(category);
			}
			if(show.equals(Constants.CACHE_KEY_ASK_CATEGORY) && Constants.Y.equals(category.getShowInQuestion())){
				filterChildren.add(category);
			}
			if(show.equals(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY) && Constants.Y.equals(category.getShowInKnowledge())){
				filterChildren.add(category);
			}
			if(show.equals(Constants.CACHE_KEY_EXAM_CATEGORY) && Constants.Y.equals(category.getShowInExam())){
				filterChildren.add(category);
			}
			singleCategoryCache.put(Constants.CACHE_KEY_CATEGORY + category.getCategoryId(), category);
		}
		c.setChildren(filterChildren);
	}
	
	public void refreshCategoryCache(){
		List<Category> list = this.categoryService.findCategoryList(null);
		for(Category category : list){
			if(Constants.Y.equals(category.getShowInBbs())){
				categoryCache.get(Constants.CACHE_KEY_BBS_CATEGORY).add(category);
				filterChildren(category, Constants.CACHE_KEY_BBS_CATEGORY);
			}
			if(Constants.Y.equals(category.getShowInExam())){
				categoryCache.get(Constants.CACHE_KEY_EXAM_CATEGORY).add(category);
				filterChildren(category, Constants.CACHE_KEY_EXAM_CATEGORY);
			}
			if(Constants.Y.equals(category.getShowInKnowledge())){
				categoryCache.get(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY).add(category);
				filterChildren(category, Constants.CACHE_KEY_KNOWLEDGE_CATEGORY);
			}
			if(Constants.Y.equals(category.getShowInQuestion())){
				categoryCache.get(Constants.CACHE_KEY_ASK_CATEGORY).add(category);
				filterChildren(category, Constants.CACHE_KEY_ASK_CATEGORY);
			}
			singleCategoryCache.put(Constants.CACHE_KEY_CATEGORY + category.getCategoryId(), category);
		}
	}
	
	public List<Category> getBbsCategories(){
		return categoryCache.get(Constants.CACHE_KEY_BBS_CATEGORY);
	}
	public List<Category> getKnowledgeCategories(){
		return categoryCache.get(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY);
	}
	public static Category getCategoryById(Integer categoryId){
		return singleCategoryCache.get(Constants.CACHE_KEY_CATEGORY + categoryId);
	}
}

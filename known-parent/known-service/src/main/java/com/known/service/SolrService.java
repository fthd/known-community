package com.known.service;

import com.known.common.model.SolrBean;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;

import java.util.List;

public interface SolrService {
	void addArticle(SolrBean solrBean);
	
	void addArticleBatch(List<SolrBean> solrBeans);
	
	PageResult<SolrBean> findSolrBeanByPage(String keyWord, String articleType,
												   Integer pageNum, Integer countTotal) throws BussinessException;
}

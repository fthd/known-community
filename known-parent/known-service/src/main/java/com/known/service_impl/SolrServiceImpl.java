package com.known.service_impl;

import com.known.common.enums.PageSize;
import com.known.common.model.SolrBean;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.SolrQuery;
import com.known.service.SolrService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SolrServiceImpl implements SolrService {
	
	private static final int KEYWORDMAXLENGTH = 30;
	
	public void addArticle(SolrBean solrBean) {
		/*HttpSolrServer server = SolrContext.getHttpSolrServer();
		try {
			server.addBean(solrBean);
			server.optimize();
			server.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}*/
	}

	public void addArticleBatch(List<SolrBean> solrBeans) {
		/*HttpSolrServer server = SolrContext.getHttpSolrServer();
		try {
			server.addBeans(solrBeans);
			server.optimize();
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
	}

	public PageResult<SolrBean> findSolrBeanByPage(String keyWord,
												   String articleType, Integer pageNum, Integer countTotal)
			throws BussinessException {
		/*if(StringUtil.isEmpty(keyWord.trim()) || keyWord.trim().length() > KEYWORDMAXLENGTH){
			throw new BussinessException("参数错误");
		}
		countTotal = countTotal == null ? 0 : countTotal; 
		SolrQuery solrQuery = new SolrQuery();
		Page page = new Page();
		solrQuery.set("q", "(title:" + keyWord + " OR " + "content:" + keyWord + ") AND articleType:" + articleType);
		if(pageNum == null || pageNum == 1){
			solrQuery.setStart(1);
			solrQuery.setRows(PageSize.PAGE_SIZE20.getSize());
			page.setPageSize(PageSize.PAGE_SIZE20.getSize());
		}
		else {
			page = new Page(pageNum, countTotal, PageSize.PAGE_SIZE20.getSize());
			solrQuery.setStart(page.getStart());
			solrQuery.setRows(page.getEnd());
		}
		solrQuery.setHighlight(true)
						.addHighlightField("title")                     
				        .setHighlightSimplePre("<span style=\"color:red\">")
				        .setHighlightSimplePost("</span>");
		HttpSolrServer server = SolrContext.getHttpSolrServer();
		PageResult<SolrBean> result = new PageResult<SolrBean>();
		try {
			QueryResponse response = server.query(solrQuery);
			if(response != null){
				int count = (int) response.getResults().getNumFound();
				page.setCount(count);
				List<SolrBean> solrBeans = response.getBeans(SolrBean.class);
				Map<String, Map<String, List<String>>> map = response.getHighlighting();  
				for(SolrBean solrBean : solrBeans){
					Map<String, List<String>> hlText = map.get(solrBean.getId());
					if(hlText != null){
						List<String> titles = hlText.get("title");
						if(titles != null && !titles.isEmpty()){
							solrBean.setTitle(titles.get(0));
						}
					}
				}
				result =  new PageResult<SolrBean>(page, solrBeans);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;*/
		return null;
	}

}

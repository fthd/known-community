package com.known.service_impl;

import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.PageSizeEnum;
import com.known.common.enums.TextLengthEnum;
import com.known.common.model.*;
import com.known.common.utils.StringUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.*;
import com.known.manager.query.*;
import com.known.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionMapper<Collection, CollectionQuery> collectionMapper;
	
	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;
	
	@Autowired
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;
	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void addCollection(Collection collection)
			throws BussinessException {
		if(collection.getArticleId() == null || collection.getArticleType() == null 
				|| StringUtil.isEmpty(collection.getTitle()) ||
				collection.getTitle().length() > TextLengthEnum.TEXT_300_LENGTH.getLength()){
			throw new BussinessException("参数错误");
		}
		
		CollectionQuery collectionQuery = new CollectionQuery(collection.getArticleId(), 
				collection.getArticleType(), collection.getUserId());
		Collection c = this.findCollectionByKey(collectionQuery);
		if(c != null){
			throw new BussinessException("您已经收藏过了");
		}
		collection.setCreateTime(new Date());
		this.collectionMapper.insert(collection);
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddCollectionCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(collection.getArticleId());
		if(collection.getArticleType() == ArticleTypeEnum.TOPIC){
			this.topicMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		else if(collection.getArticleType() == ArticleTypeEnum.KNOWLEDGE){
			this.knowledgeMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		else if(collection.getArticleType() == ArticleTypeEnum.Ask){
			this.askMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		else{
			throw new BussinessException("参数错误");
		}
	}

	public Collection findCollectionByKey(CollectionQuery collectionQuery) {
		List<Collection> collectionList = this.collectionMapper.selectList(collectionQuery);
		if(collectionList.isEmpty()){
			return null;
		}
		return collectionList.get(0);
	}

	public PageResult<Collection> findCollectionByPage(
			CollectionQuery collectionQuery) {
		int count = this.collectionMapper.selectCount(collectionQuery);
		int pageSize = PageSizeEnum.PAGE_SIZE20.getSize();
		int pageNum = collectionQuery.getPageNum() == 1? 1:collectionQuery.getPageNum();
		Page page = new Page(pageNum, count, pageSize);
		collectionQuery.setPage(page);
		List<Collection> list = this.collectionMapper.selectList(collectionQuery);
		return new PageResult<>(page, list);
	}

	public void deleteCollection(Collection collection) {
		this.collectionMapper.delete(collection);
	}

}

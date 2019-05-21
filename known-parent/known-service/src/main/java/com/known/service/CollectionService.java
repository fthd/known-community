package com.known.service;

import com.known.common.model.Collection;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CollectionQuery;

public interface CollectionService {
	
	void addCollection(Collection collection) throws BussinessException;
	
	Collection findCollectionByKey(CollectionQuery collectionQuery);
	
	PageResult<Collection> findCollectionByPage(CollectionQuery collectionQuery);
	
	void deleteCollection(Collection collection);
}

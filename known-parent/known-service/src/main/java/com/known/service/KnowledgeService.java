package com.known.service;

import com.known.common.model.Attachment;
import com.known.common.model.Knowledge;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.KnowledgeQuery;

import java.util.List;

public interface KnowledgeService {
	
	PageResult<Knowledge> findKnowledgeByPage(KnowledgeQuery knowledgeQuery);
	
	Knowledge getKnowledge(Integer knowledgeId);
	
	Knowledge showKnowledge(Integer knowledgeId, Integer userId) throws BussinessException;
	
	void addKnowledge(Knowledge knowledge, Attachment attachment) throws BussinessException;
	
	List<Knowledge> findKnowledgeList();
	
	void deleteBatch(Integer[] ids)throws BussinessException;
	
	void updateStatusBatch(Integer[] ids) throws BussinessException;
}



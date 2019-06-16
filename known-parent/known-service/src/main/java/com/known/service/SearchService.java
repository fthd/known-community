package com.known.service;


import com.known.common.model.AskEsModel;
import com.known.common.model.KnowledgeEsModel;
import com.known.common.model.TopicEsModel;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;

public interface SearchService {

    PageResult<TopicEsModel> findTopicEsByPage(String keyword, Integer pageNum, Integer countTotal);

    PageResult<KnowledgeEsModel> findKnowledgeEsByPage(String keyword, Integer pageNum, Integer countTotal);

    PageResult<AskEsModel> findAskEsByPage(String keyword, Integer pageNum, Integer countTotal) throws BussinessException;

}

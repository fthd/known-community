package com.known.service;

import com.known.common.model.Ask;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.AskQuery;

import java.util.List;

public interface AskService {
	
	PageResult<Ask> findAskByPage(AskQuery askQuery);
	
	int findCount(AskQuery askQuery);
	
	void addAsk(Ask ask) throws BussinessException;
	
	void setBestAnswer(String bestAnswerId, String askId, String userId) throws BussinessException;
	
	Ask getAskById(String askId);
	
	Ask showAsk(String askId, String flag) throws BussinessException;
	
	List<Ask> findTopUsers();
	
	List<Ask> findAskList();
	
	void deleteBatch(String[] ids) throws BussinessException;
	
}

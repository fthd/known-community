package com.known.service;

import com.known.common.model.Message;
import com.known.common.model.MessageParams;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.MessageQuery;
import org.springframework.scheduling.annotation.Async;

public interface MessageService {
	
	@Async
	void createMessage(MessageParams messageParams);
	
	Message getMessageById(String id, String userId);
	
	PageResult<Message> findMessageByPage(MessageQuery messageQuery);
	
	int findMessageCount(MessageQuery messageQuery);
	
	void update(String[] ids, String userId)throws BussinessException;
	
	void delMessage(String userId, String[] ids)throws BussinessException;
	
}

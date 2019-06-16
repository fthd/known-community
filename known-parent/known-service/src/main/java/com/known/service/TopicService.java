package com.known.service;

import com.known.common.model.Attachment;
import com.known.common.model.Topic;
import com.known.common.model.TopicVote;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.TopicQuery;

import java.util.List;

public interface TopicService {
	
	PageResult<Topic> findTopicByPage(TopicQuery topicQuery);

	PageResult<Topic> findTopicByPage4(TopicQuery topicQuery);
	
	void addTopic(Topic topic, TopicVote topicVote, String[] voteTitle, Attachment file)throws BussinessException;
	
	
	Topic showTopic(String topicId) throws BussinessException;
	
	
	Topic getTopic(String topicId);
	
	List<Topic> findActiveUsers();
	
	Integer findCount(TopicQuery topicQuery);
	
	List<Topic> findTopicList();
	
	void updateTopicEssence(String[] topicId, int essence)throws BussinessException;
	
	void updateTopicStick(String[] topicId, int stick)throws BussinessException;
	
	void deleteBatch(String[] topicIds) throws BussinessException;
	
}

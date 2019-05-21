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
	
	
	void addTopic(Topic topic, TopicVote topicVote, String[] voteTitle, Attachment file)throws BussinessException;
	
	
	Topic showTopic(Integer topicId) throws BussinessException;
	
	
	Topic getTopic(Integer topicId);
	
	List<Topic> findActiveUsers();
	
	Integer findCount(TopicQuery topicQuery);
	
	List<Topic> findTopicList();
	
	void updateTopicEssence(Integer[] topicId, int essence)throws BussinessException;
	
	void updateTopicStick(Integer[] topicId, int stick)throws BussinessException;
	
	void deleteBatch(Integer[] topicIds) throws BussinessException;
	
}

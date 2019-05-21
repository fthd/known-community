package com.known.service;

import com.known.common.model.TopicVote;
import com.known.exception.BussinessException;

public interface TopicVoteService {
	
	void addVote(TopicVote topicVote, String[] voteTitle)throws BussinessException;
	
	TopicVote getTopicVote(Integer topicId, Integer userId);
	
	TopicVote doVote(Integer voteId, Integer voteType, Integer[] voteDetailId, Integer userId, Integer topicId)throws BussinessException;
}

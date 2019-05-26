package com.known.service;

import com.known.common.model.TopicVote;
import com.known.exception.BussinessException;

public interface TopicVoteService {
	
	void addVote(TopicVote topicVote, String[] voteTitle)throws BussinessException;
	
	TopicVote getTopicVote(String topicId, String userId);
	
	TopicVote doVote(String voteId, Integer voteType, String[] voteDetailId, String userId, String topicId)throws BussinessException;
}

package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicVoteDetailMapper<T, Q> extends BaseMapper<T, Q> {
	void insertBatch(@Param("voteDetailList") List<T> voteDetailList);
	
	void updateVoteCountBatch(@Param("list") List<String> list);
}
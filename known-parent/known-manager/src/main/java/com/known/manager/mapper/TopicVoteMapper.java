package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicVoteMapper<T, Q> extends BaseMapper<T, Q>{
    T selectVoteByTopicId(@Param("topicId") Integer topicId);
}
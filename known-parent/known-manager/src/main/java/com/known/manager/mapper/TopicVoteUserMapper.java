package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicVoteUserMapper<T, Q> extends BaseMapper<T, Q> {
	void insertBatch(@Param("list") List<T> list);
}
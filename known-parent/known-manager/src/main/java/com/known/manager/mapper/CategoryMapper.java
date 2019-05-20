package com.known.manager.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper<T, Q> extends BaseMapper<T, Q> {

	List<T> selectCategory4TopicCount(Q q);

}
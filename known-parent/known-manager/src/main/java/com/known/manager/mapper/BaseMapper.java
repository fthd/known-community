package com.known.manager.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseMapper<T, Q> {
	void insert(T t);
	
	List<T> selectList(Q q);
	
	Integer selectCount(Q q);
	
	void update(T t);

	void delete(T t);	
}

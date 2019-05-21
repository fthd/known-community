package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper<T, Q> extends BaseMapper<T, Q> {
	void insertBatch(@Param("list") List<T> list);
	
	void delete(@Param("userId") Integer userId, @Param("ids") Integer[] ids);
	
	void update(@Param("userId") Integer userId, @Param("ids") Integer[] ids);
}
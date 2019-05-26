package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper<T, Q> extends BaseMapper<T, Q> {
	void insertBatch(@Param("list") List<T> list);
	
	void delete(@Param("userId") String userId, @Param("ids") String[] ids);
	
	void update(@Param("userId") String userId, @Param("ids") String[] ids);
}
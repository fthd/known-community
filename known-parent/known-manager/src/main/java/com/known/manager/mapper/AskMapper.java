package com.known.manager.mapper;

import com.known.manager.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AskMapper<T, Q> extends BaseMapper<T, Q> {
	void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
	
	List<T> selectTopUser(Q q);
	
	void updateBestAnswer(T t);
	
	void delete(@Param("askId") String askId);
}
package com.known.manager.mapper;

import com.known.manager.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper<T, Q> extends BaseMapper<T, Q> {
	void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
	
	void delete(@Param("blogId") Integer blogId);
}
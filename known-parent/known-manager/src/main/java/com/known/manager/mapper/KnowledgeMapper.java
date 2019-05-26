package com.known.manager.mapper;

import com.known.common.enums.StatusEnum;
import com.known.manager.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeMapper<T, Q> extends BaseMapper<T, Q> {
	void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
	
	void updateKnowledgeStatus(@Param("status") StatusEnum status, @Param("ids") String[] ids);
	
	void delete(@Param("id") String id);
}
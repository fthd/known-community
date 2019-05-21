package com.known.manager.mapper;

import com.known.common.enums.StatusEnum;
import com.known.manager.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
	
	public void updateKnowledgeStatus(@Param("status") StatusEnum status, @Param("ids") Integer[] ids);
	
	public void delete(@Param("id") Integer id);
}
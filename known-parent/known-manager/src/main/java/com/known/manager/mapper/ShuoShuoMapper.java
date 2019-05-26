package com.known.manager.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShuoShuoMapper<T, Q> extends BaseMapper<T, Q> {
	void updateShuoShuoCommentCount(String id);
	
	void updateShuoShuoLikeCount(String id);
	
	List<T> selectActiveUser4ShuoShuo(Q q);
	
	void delete(@Param("id") String id);
		
}
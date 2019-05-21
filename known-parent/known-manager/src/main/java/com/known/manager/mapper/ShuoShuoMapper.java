package com.known.manager.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShuoShuoMapper<T, Q> extends BaseMapper<T, Q> {
	void updateShuoShuoCommentCount(Integer id);
	
	void updateShuoShuoLikeCount(Integer id);
	
	List<T> selectActiveUser4ShuoShuo(Q q);
	
	void delete(@Param("id") Integer id);
		
}
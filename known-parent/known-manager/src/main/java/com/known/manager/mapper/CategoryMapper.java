package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper<T, Q> extends BaseMapper<T, Q> {

	List<T> selectCategory4TopicCount(Q q);

	void deleteIds(@Param("ids") Integer[] ids);

	void  deletePermission(@Param("ids") Integer[] ids) ;
}
package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMapper<T, Q> extends BaseMapper<T, Q> {
	void delete(@Param("ids") Integer[] ids);
}
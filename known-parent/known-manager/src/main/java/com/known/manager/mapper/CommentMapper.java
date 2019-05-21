package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper<T, Q> extends BaseMapper<T, Q> {
   List<T> selectChildren(@Param("id") Integer id);
}
package com.known.manager.mapper;

import com.known.common.model.ShuoShuoComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShuoShuoCommentMapper<T, Q> extends BaseMapper<T, Q>{
	List<ShuoShuoComment> selectListByShuoShuoId(Integer shuoShuoId);//@Param("shuoShuoId") Integer shuoShuoId
}
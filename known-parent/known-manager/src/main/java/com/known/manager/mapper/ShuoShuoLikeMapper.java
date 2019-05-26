package com.known.manager.mapper;

import com.known.common.model.ShuoShuoLike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShuoShuoLikeMapper<T, Q> extends BaseMapper<T, Q> {
	List<ShuoShuoLike> selectListByShuoShuoId(String shuoShuoId);
}
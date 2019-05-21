package com.known.service;


import com.known.common.model.Like;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.LikeQuery;

public interface LikeService {
	
	void addLike(Like like) throws BussinessException;
	
	Like findLikeByKey(LikeQuery likeQuery);
	
	PageResult<Like> findLikeByPage(LikeQuery likeQuery);
}

package com.known.service;

import com.known.common.model.Comment;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CommentQuery;

public  interface CommentService {
	
	PageResult<Comment> findCommentByPage(CommentQuery commentQuery);
	
	Comment getCommentById(Integer commentId);
	
	void addComment(Comment comment) throws BussinessException;
	
}

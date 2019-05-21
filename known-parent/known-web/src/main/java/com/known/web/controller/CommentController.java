package com.known.web.controller;

import com.known.common.enums.ResponseCode;
import com.known.common.model.Comment;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CommentQuery;
import com.known.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	@ResponseBody
	@RequestMapping("loadComment")
	public AjaxResponse<PageResult<Comment>> loadComment(HttpSession session, CommentQuery commentQuery){
		 AjaxResponse<PageResult<Comment>> ajaxResponse = new AjaxResponse<PageResult<Comment>>();
		 try {
			 PageResult<Comment> pageResult = this.commentService.findCommentByPage(commentQuery);
			 ajaxResponse.setData(pageResult);
			 ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("{}加载评论出错", e);
			ajaxResponse.setErrorMsg("加载评论出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		 return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("addComment")
	public AjaxResponse<Object> addComment(HttpSession session, Comment comment){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		this.setUserBaseInfo(Comment.class, comment, session);
		try {
			this.commentService.addComment(comment);
			ajaxResponse.setData(comment);
			 ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", sessionUser.getUserName());
		}catch (Exception e) {
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			ajaxResponse.setErrorMsg("服务器出错");
			logger.error("{}评论出错", sessionUser.getUserName());
		}
		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("addComment2")
	public AjaxResponse<Object> addComment2(HttpSession session, Comment comment){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		this.setUserBaseInfo(Comment.class, comment, session);
		try {
			this.commentService.addComment(comment);
			ajaxResponse.setData(comment);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", sessionUser.getUserName());
		}catch (Exception e) {
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			ajaxResponse.setErrorMsg("服务器出错");
			logger.error("{}评论出错", sessionUser.getUserName());
		}
		return ajaxResponse;
	}


}

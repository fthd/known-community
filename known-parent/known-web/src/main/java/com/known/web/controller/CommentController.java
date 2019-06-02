package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.model.Comment;
import com.known.common.model.SessionUser;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CommentQuery;
import com.known.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;

	@Resource
	private UserConfig userConfig;

	@RequestMapping("/loadComment")
	public OutResponse<PageResult<Comment>> loadComment(CommentQuery commentQuery){
		 OutResponse<PageResult<Comment>> outResponse = new OutResponse<>();
		 try {
			 PageResult<Comment> pageResult = commentService.findCommentByPage(commentQuery);
			 outResponse.setData(pageResult);
			 outResponse.setCode(CodeEnum.SUCCESS);
		 }catch (BussinessException e) {
				 logger.error("{}加载评论出错", e);
				 outResponse.setMsg(e.getLocalizedMessage());
				 outResponse.setCode(CodeEnum.BUSSINESSERROR);
		} catch (Exception e) {
			logger.error("{}加载评论出错", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.SERVERERROR);
		}
		 return outResponse;
	}

	@RequestMapping("/addComment")
	public OutResponse<Object> addComment(HttpSession session, Comment comment){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		setUserBaseInfo(Comment.class, comment, session);
		try {
			// 生成评论id
			comment.setId(UUIDUtil.getUUID());
			commentService.addComment(comment);
			outResponse.setData(comment);
			 outResponse.setCode(CodeEnum.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", sessionUser.getUserName());
		}catch (Exception e) {
			outResponse.setCode(CodeEnum.SERVERERROR);
			outResponse.setMsg("服务器出错");
			logger.error("{}评论出错", sessionUser.getUserName());
		}
		return outResponse;
	}


	@RequestMapping("/addComment2")
	public OutResponse<Object> addComment2(HttpSession session, Comment comment){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		setUserBaseInfo(Comment.class, comment, session);
		try {
			commentService.addComment(comment);
			outResponse.setData(comment);
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", sessionUser.getUserName());
		}catch (Exception e) {
			outResponse.setCode(CodeEnum.SERVERERROR);
			outResponse.setMsg("服务器出错");
			logger.error("{}评论出错", sessionUser.getUserName());
		}
		return outResponse;
	}


}

package com.known.web.controller;

import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.ResponseCode;
import com.known.common.model.*;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.*;
import com.known.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserHomeController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(UserHomeController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShuoShuoService shuoShuoService;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private AskService askService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private BlogCategoryService blogCategoryService;
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="/{userId}")
	public ModelAndView user(HttpSession session, @PathVariable Integer userId){
		ModelAndView view = new ModelAndView("/page/user/home");
		try {
			User user = this.userService.findUserInfo4UserHome(userId);
			user.setUserIcon(user.getUserIcon());
			view.addObject("user", user);
			UserFriendQuery userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(this.getUserid(session));
			userFriendQuery.setFriendUserId(userId);
			view.addObject("focusType", this.userFriendService.findFocusType4UserHome(userFriendQuery));
			//获取粉丝和关注数量
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setFriendUserId(userId);
			view.addObject("fansCount", this.userFriendService.findCount(userFriendQuery));
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(userId);
			view.addObject("focusCount", this.userFriendService.findCount(userFriendQuery));
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("loadShuoShuos")
	public AjaxResponse<Object> loadShuoShuos(HttpSession session, ShuoShuoQuery shuoShuoQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<ShuoShuo> pageResult = this.shuoShuoService.findShuoShuoList(shuoShuoQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载说说异常", e);
			ajaxResponse.setErrorMsg("加载说说出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	
	@ResponseBody
	@RequestMapping("loadShuoShuoDetail")
	public AjaxResponse<Object> loadShuoShuoDetail(HttpSession session, ShuoShuoQuery shuoShuoQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			ShuoShuo shuoShuo = this.shuoShuoService.findShuoShuo(shuoShuoQuery);
			ajaxResponse.setData(shuoShuo);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载说说异常", e);
			ajaxResponse.setErrorMsg("加载说说出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("publicShuoShuoComment")
	public AjaxResponse<Object> publicShuoShuo(HttpSession session, ShuoShuoComment shuoShuoComment){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			this.setUserBaseInfo(ShuoShuoComment.class, shuoShuoComment, session);
			this.shuoShuoService.addShuoShuoComment(shuoShuoComment);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(shuoShuoComment);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", shuoShuoComment.getUserName());
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadUserFriend")
	public AjaxResponse<Object> loadUserFriend(HttpSession session, int pageNum){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		int userId = this.getUserid(session);
		UserFriendQuery userFriendQuery = new UserFriendQuery();
		userFriendQuery.setUserId(userId);
		userFriendQuery.setPageNum(pageNum);
		PageResult<UserFriend> pageResult = this.userFriendService.findFriendList(userFriendQuery);
		ajaxResponse.setData(pageResult);
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("doShuoShuoLike")
	public AjaxResponse<Object> doShuoShuoLike(HttpSession session, ShuoShuoLike shuoShuoLike){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		this.setUserBaseInfo(ShuoShuoLike.class, shuoShuoLike, session);
		try {
			this.shuoShuoService.doShuoShuoLike(shuoShuoLike);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(shuoShuoLike);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}点赞出错", shuoShuoLike.getUserName());
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadTopic")
	public AjaxResponse<Object> loadTopic(HttpSession session, TopicQuery topicQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<Topic> pageResult = this.topicService.findTopicByPage(topicQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载话题异常", e);
			ajaxResponse.setErrorMsg("加载话题出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadAsk")
	public AjaxResponse<Object> loadAsk(HttpSession session, AskQuery askQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<Ask> pageResult = this.askService.findAskByPage(askQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载问答异常", e);
			ajaxResponse.setErrorMsg("加载问答出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadKnowledge")
	public AjaxResponse<Object> loadKnowledge(HttpSession session, KnowledgeQuery knowledgeQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try {
			PageResult<Knowledge> pageResult = this.knowledgeService.findKnowledgeByPage(knowledgeQuery);
			ajaxResponse.setData(pageResult);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("加载知识库异常", e);
			ajaxResponse.setErrorMsg("加载知识库出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadFocus")
	public AjaxResponse<Object> loadUserFriend(HttpSession session, UserFriendQuery userFriendQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			PageResult<UserFriend> pageResult = this.userFriendService.findFriendList(userFriendQuery);
			ajaxResponse.setData(pageResult);
		} catch (Exception e) {
			logger.error("加载关注用户异常", e);
			ajaxResponse.setErrorMsg("加载关注用户出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("loadFans")
	public AjaxResponse<Object> loadUserFans(HttpSession session, UserFriendQuery userFriendQuery){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			PageResult<UserFriend> pageResult = this.userFriendService.findFansList(userFriendQuery);
			ajaxResponse.setData(pageResult);
		} catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("加载粉丝出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "/{userId}/blog", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, @PathVariable Integer userId, BlogQuery blogQuery){
		ModelAndView view = new ModelAndView("/page/user/blog");
		try {
			User user = this.userService.findUserInfo4UserHome(userId);
			UserFriendQuery userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(this.getUserid(session));
			userFriendQuery.setFriendUserId(userId);
			view.addObject("focusType", this.userFriendService.findFocusType4UserHome(userFriendQuery));
			blogQuery.setStatus(BlogStatusEnum.PUBLIC);
			List<BlogCategory> categoryList = this.blogCategoryService.findBlogCategoryList(userId);
			PageResult<Blog> result = this.blogService.findBlogByPage(blogQuery);
			view.addObject("user", user);
			view.addObject("categoryList", categoryList);
			view.addObject("result", result);
			
			//获取粉丝和关注数量
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setFriendUserId(userId);
			view.addObject("fansCount", this.userFriendService.findCount(userFriendQuery));
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(userId);
			view.addObject("focusCount", this.userFriendService.findCount(userFriendQuery));
		} catch (Exception e) {
			logger.error("获取话题信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@RequestMapping(value = "/{userId}/blog/{blogId}", method = RequestMethod.GET)
	public ModelAndView blogDetail(HttpSession session, @PathVariable Integer blogId){
		ModelAndView view = new ModelAndView("/page/user/blog_detail");
		try {
			Blog blog = this.blogService.showBlog(blogId);
			view.addObject("topic", blog);
		} catch (Exception e) {
			logger.error("获取话题信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@RequestMapping(value = "/{userId}/shuoshuo/{id}")
	public ModelAndView shuoshuo(HttpSession session, @PathVariable Integer userId, @PathVariable Integer id){
		ModelAndView view = new ModelAndView("/page/user/shuoshuo");
		try {
			User user = this.userService.findUserInfo4UserHome(userId);
			view.addObject("user", user);
			view.addObject("id", id);
			UserFriendQuery userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(this.getUserid(session));
			userFriendQuery.setFriendUserId(userId);
			view.addObject("focusType", this.userFriendService.findFocusType4UserHome(userFriendQuery));
			
			//获取粉丝和关注数量
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setFriendUserId(userId);
			view.addObject("fansCount", this.userFriendService.findCount(userFriendQuery));
			userFriendQuery = new UserFriendQuery();
			userFriendQuery.setUserId(userId);
			view.addObject("focusCount", this.userFriendService.findCount(userFriendQuery));
		} catch (Exception e) {
			logger.error("获取说说信息失败：", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("focus.action")
	public AjaxResponse<Object> focus(HttpSession session, UserFriend userFriend){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			this.setUserBaseInfo(UserFriend.class, userFriend, session);
			this.userFriendService.addFocus(userFriend);
		}catch (BussinessException e) {
			logger.error("关注异常", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		} 
		catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("关注出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("cancel_focus.action")
	public AjaxResponse<Object> cancel_focus(HttpSession session, UserFriend userFriend){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		try{
			this.setUserBaseInfo(UserFriend.class, userFriend, session);
			this.userFriendService.cancelFocus(userFriend);
		}catch (BussinessException e) {
			logger.error("关注异常", e);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		} 
		catch (Exception e) {
			logger.error("加载用户粉丝异常", e);
			ajaxResponse.setErrorMsg("关注出错,请重试");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
}

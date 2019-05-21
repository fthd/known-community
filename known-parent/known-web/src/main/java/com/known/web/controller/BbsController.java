package com.known.web.controller;

import com.known.cache.CategoryCache;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.ResponseCode;
import com.known.common.enums.TopicType;
import com.known.common.enums.VoteType;
import com.known.common.model.*;
import com.known.common.utils.Constants;
import com.known.common.utils.DateUtil;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CategoryQuery;
import com.known.manager.query.TopicQuery;
import com.known.service.AttachmentService;
import com.known.service.CategoryService;
import com.known.service.TopicService;
import com.known.service.TopicVoteService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bbs")
public class BbsController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(BbsController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private TopicVoteService topicVoteService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private CategoryCache categoryCache;
	
	@RequestMapping
	public ModelAndView bbs(HttpSession session){
		ModelAndView view = new ModelAndView("/page/bbs/bbs");
		CategoryQuery categoryQuery = new CategoryQuery();
		Date date = new Date();
		String curDate = DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		categoryQuery.setStartDate(curDate);
		categoryQuery.setEndDate(curDate);
		view.addObject("categories", categoryService.findCategory4TopicCount(categoryQuery));
		view.addObject("activeUser", this.topicService.findActiveUsers());
		//获取总话题数
		view.addObject("count", this.topicService.findCount(null));
		//获取今日话题
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(curDate);
		topicQuery.setEndDate(curDate);
		view.addObject("today", this.topicService.findCount(topicQuery));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		topicQuery.setStartDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		Integer yesterdayCount = this.topicService.findCount(topicQuery);
		view.addObject("yesterdayCount",  yesterdayCount);
		return view;
	}
	
	@RequestMapping("prePublicTopic")
	public ModelAndView prePublicTopic(HttpSession session){
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ModelAndView view = new ModelAndView("/page/login");
			return view;
		}
		ModelAndView view = new ModelAndView("/page/bbs/publicTopic");
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInBbs(Constants.Y);
		view.addObject("topicType", TopicType.values());
		view.addObject("voteType", VoteType.values());
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadCategories")
	public AjaxResponse<List<Category>> loadCategories(){
		AjaxResponse<List<Category>> ajaxResponse = new AjaxResponse<List<Category>>();
		try {
			ajaxResponse.setData(this.categoryCache.getBbsCategories());
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			return ajaxResponse;
		} catch (Exception e) {
			ajaxResponse.setErrorMsg("加载分类出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}加载分类出错",e);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("publicTopic")
	public AjaxResponse<Integer> publicTopic(HttpSession session, Topic topic, TopicVote topicVote,
											 String[] voteContent, Attachment attachment){
		AjaxResponse<Integer> ajaxResponse = new AjaxResponse<>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		try {
			this.setUserBaseInfo(Topic.class, topic, session);
			this.topicService.addTopic(topic, topicVote, voteContent, attachment);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(topic.getTopicId());
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}发表话题失败", sessionUser.getUserName());
		} catch (Exception e) {
			ajaxResponse.setErrorMsg("服务器出错,话题发表失败");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}发表话题失败", sessionUser.getUserName());
		}
		return ajaxResponse;
	}
	
	
	@RequestMapping(value = "/board/{pCategoryId}", method= RequestMethod.GET)
	public ModelAndView board(@PathVariable Integer pCategoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("/page/bbs/bbs_list");
		Category pCategory = this.categoryService.findCategoryBypCategoryId(pCategoryId);
		view.addObject("pCategory", pCategory);
		
		PageResult<Topic> result = this.topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		
		//获取分类总话题数
		TopicQuery query = new TopicQuery();
		query.setPCategoryId(pCategory.getCategoryId());
		view.addObject("count", this.topicService.findCount(query));
		//获取今日话题数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", this.topicService.findCount(query));
		return view;
	}
	
	
	@RequestMapping(value = "/sub_board/{categoryId}", method= RequestMethod.GET)
	public ModelAndView sub_board(@PathVariable Integer categoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("/page/bbs/bbs_list");
		Category pCategory = this.categoryService.findCategoryByCategoryId(categoryId);
		view.addObject("pCategory", pCategory);
		view.addObject("category", CategoryCache.getCategoryById(categoryId));
		PageResult<Topic> result = this.topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		//获取分类总话题数
		TopicQuery query = new TopicQuery();
		query.setPCategoryId(pCategory.getCategoryId());
		view.addObject("count", this.topicService.findCount(query));
		//获取今日话题数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", this.topicService.findCount(query));
		return view;
	}
	
	@RequestMapping("/{topicId}")
	public ModelAndView bbsDetail(@PathVariable Integer topicId){
		ModelAndView view = new ModelAndView("/page/bbs/bbs_detail");
		try {
			Topic topic = this.topicService.showTopic(topicId);
			view.addObject("topic", topic);
		} catch (Exception e) {
			logger.error("{}话题加载出错", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
		}
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("loadVote")
	public AjaxResponse<Object> loadVote(HttpSession session, Integer topicId){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		Integer userId = null;
		try {
			userId = sessionUser==null ? null : sessionUser.getUserid();
			TopicVote topicVote = this.topicVoteService.getTopicVote(topicId, userId);
			ajaxResponse.setData(topicVote);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("{}投票加载出错", e);
			ajaxResponse.setErrorMsg("投票加载失败");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("doVote")
	public AjaxResponse<Object> doVote(HttpSession session, TopicVote  topicVote, Integer[] voteDtlId){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			TopicVote topicVote2 = this.topicVoteService.doVote(topicVote.getVoteId(), 
					topicVote.getVoteType().getType(), voteDtlId, sessionUser.getUserid(), topicVote.getTopicId());
			ajaxResponse.setData(topicVote2);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("{}投票出错", e);
			ajaxResponse.setErrorMsg("投票失败");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}

	@ResponseBody
	@RequestMapping("refreshCategoryCache")
	public String refreshCategoryCache(){
		categoryCache.refreshCategoryCache();
		return "ok";
	}
}

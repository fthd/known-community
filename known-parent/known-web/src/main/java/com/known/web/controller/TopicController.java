package com.known.web.controller;

import com.known.cache.CategoryCache;
import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.TopicTypeEnum;
import com.known.common.enums.VoteTypeEnum;
import com.known.common.model.*;
import com.known.common.utils.Constants;
import com.known.common.utils.DateUtil;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CategoryQuery;
import com.known.manager.query.TopicQuery;
import com.known.service.CategoryService;
import com.known.service.TopicService;
import com.known.service.TopicVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private TopicVoteService topicVoteService;

	@Autowired
	private CategoryCache categoryCache;

	@Resource
	private UserConfig userConfig;

	@Resource
	private UrlConfig urlConfig;

	/**
	 * 话题中心首页
	 * @return
	 */
	@RequestMapping("/topic")
	public ModelAndView topic(){
		ModelAndView view = new ModelAndView("/page/topic/topic");
		CategoryQuery categoryQuery = new CategoryQuery();
		Date date = new Date();
		String curDate = DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		categoryQuery.setStartDate(curDate);
		categoryQuery.setEndDate(curDate);
		view.addObject("categories", categoryService.findCategory4TopicCount(categoryQuery));
		view.addObject("activeUser", topicService.findActiveUsers());
		//获取总话题数
		view.addObject("count", topicService.findCount(null));
		//获取今日话题
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setStartDate(curDate);
		topicQuery.setEndDate(curDate);
		view.addObject("today", topicService.findCount(topicQuery));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		topicQuery.setStartDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		topicQuery.setEndDate(DateUtil.format(calendar.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		Integer yesterdayCount = topicService.findCount(topicQuery);
		view.addObject("yesterdayCount",  yesterdayCount);
		return view;
	}
	
	@RequestMapping("/prePublicTopic")
	public ModelAndView prePublicTopic(HttpSession session){
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			ModelAndView view = new ModelAndView("/page/login");
			return view;
		}
		ModelAndView view = new ModelAndView("/page/topic/publicTopic");
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInTopic(Constants.Y);
		view.addObject("topicType", TopicTypeEnum.values());
		view.addObject("voteType", VoteTypeEnum.values());
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/loadCategories")
	public OutResponse<List<Category>> loadCategories(){
		OutResponse<List<Category>> outResponse = new OutResponse<>();
		try {
			outResponse.setData(categoryCache.getTopicCategories());
			outResponse.setCode(CodeEnum.SUCCESS);
			return outResponse;
		} catch (Exception e) {
			outResponse.setMsg("加载分类出错");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("{}加载分类出错",e);
		}
		return outResponse;
	}

	/**
	 * 发布话题
	 * @param session
	 * @param topic
	 * @param topicVote
	 * @param voteContent
	 * @param attachment
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/publicTopic")
	public OutResponse<String> publicTopic(HttpSession session, Topic topic, TopicVote topicVote,
											 String[] voteContent, Attachment attachment){
		OutResponse<String> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		try {
			setUserBaseInfo(Topic.class, topic, session);
			topic.setTopicId(UUIDUtil.getUUID());
			topicService.addTopic(topic, topicVote, voteContent, attachment);
			outResponse.setCode(CodeEnum.SUCCESS);
			outResponse.setData(topic.getTopicId());
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			logger.error("{}发表话题失败", sessionUser.getUserName());
		} catch (Exception e) {
			outResponse.setMsg("服务器出错,话题发表失败");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("{}发表话题失败", sessionUser.getUserName());
		}
		return outResponse;
	}

	/**
	 * 父级分类列表
	 * @param pCategoryId
	 * @param topicQuery
	 * @return
	 */
	@RequestMapping(value = "/board/{pCategoryId}", method= RequestMethod.GET)
	public ModelAndView board(@PathVariable String pCategoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("/page/topic/topic_list");
		Category pCategory = categoryService.findCategoryBypCategoryId(pCategoryId);
		view.addObject("pCategory", pCategory);
		
		PageResult<Topic> result = topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		
		//获取分类总话题数
		TopicQuery query = new TopicQuery();
		query.setPCategoryId(pCategory.getCategoryId());
		view.addObject("count", topicService.findCount(query));
		//获取今日话题数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", topicService.findCount(query));
		return view;
	}

	/**
	 * 子模块分类列表
	 * @param categoryId
	 * @param topicQuery
	 * @return
	 */
	@RequestMapping(value = "/sub_board/{categoryId}", method= RequestMethod.GET)
	public ModelAndView sub_board(@PathVariable String categoryId, TopicQuery topicQuery){
		ModelAndView view = new ModelAndView("/page/topic/topic_list");
		Category pCategory = categoryService.findCategoryByCategoryId(categoryId);
		view.addObject("pCategory", pCategory);
		view.addObject("category", CategoryCache.getCategoryById(categoryId));
		PageResult<Topic> result = topicService.findTopicByPage(topicQuery);
		view.addObject("result", result);
		//获取分类总话题数
		TopicQuery query = new TopicQuery();
		query.setPCategoryId(pCategory.getCategoryId());
		view.addObject("count", topicService.findCount(query));
		//获取今日话题数
		Date date = new Date();
		query.setStartDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		query.setEndDate(DateUtil.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayCount", topicService.findCount(query));
		return view;
	}

	/**
	 * 加载话题详情
	 * @param topicId
	 * @return
	 */
	@RequestMapping("/{topicId}")
	public ModelAndView topicDetail(@PathVariable String topicId){
		ModelAndView view = new ModelAndView("/page/topic/topic_detail");
		try {
			Topic topic = topicService.showTopic(topicId);
			// 设置分类名称
			topic.setCategoryName(CategoryCache.getCategoryById(topic.getCategoryId()).getName());
			topic.setPCategoryName(CategoryCache.getCategoryById(topic.getPCategoryId()).getName());
			view.addObject("topic", topic);
		} catch (Exception e) {
			logger.error("{}话题加载出错", e);
			view.setViewName("redirect:" + urlConfig.getError_404());
		}
		return view;
	}
	

	@RequestMapping("/loadVote")
	public OutResponse<Object> loadVote(HttpSession session, String topicId){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		String userId = null;
		try {
			userId = sessionUser==null ? null : sessionUser.getUserid();
			TopicVote topicVote = topicVoteService.getTopicVote(topicId, userId);
			outResponse.setData(topicVote);
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("{}投票加载出错", e);
			outResponse.setMsg("投票加载失败");
			outResponse.setCode(CodeEnum.SERVERERROR);
		}
		return outResponse;
	}

	@RequestMapping("/doVote")
	public OutResponse<Object> doVote(HttpSession session, TopicVote  topicVote, String[] voteDtlId){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			TopicVote topicVote2 = topicVoteService.doVote(topicVote.getVoteId(), 
					topicVote.getVoteType().getType(), voteDtlId, sessionUser.getUserid(), topicVote.getTopicId());
			outResponse.setData(topicVote2);
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("{}投票出错", e);
			outResponse.setMsg("投票失败");
			outResponse.setCode(CodeEnum.SERVERERROR);
		}
		return outResponse;
	}

	@RequestMapping("/refreshCategoryCache")
	public String refreshCategoryCache(){
		categoryCache.refreshCategoryCache();
		return "ok";
	}
}

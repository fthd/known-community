package com.known.web.controller;

import com.known.cache.CategoryCache;
import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.model.Attachment;
import com.known.common.model.Category;
import com.known.common.model.Knowledge;
import com.known.common.model.SessionUser;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.KnowledgeQuery;
import com.known.service.KnowledgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private CategoryCache categoryCache;

	@Resource
	private UserConfig userConfig;

	@Resource
	private UrlConfig urlConfig;
	
	@RequestMapping("/knowledge")
	public ModelAndView knowledge(KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping(value = "/pCategoryId/{pCategoryId}", method= RequestMethod.GET)
	public ModelAndView pCategoryId(@PathVariable String pCategoryId, KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("KnwoledgeTitleCategory", CategoryCache.getCategoryById(pCategoryId));
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping(value = "/categoryId/{categoryId}", method= RequestMethod.GET)
	public ModelAndView categoryId(@PathVariable String categoryId, KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("KnwoledgeTitleCategory", CategoryCache.getCategoryById(categoryId));
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping("/{knowledgeId}")
	public ModelAndView knowledgeDetail(@PathVariable String knowledgeId, HttpSession session){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge_detail");
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		String userId = null;
		try {
			userId = sessionUser==null ? null : sessionUser.getUserid();
			Knowledge topic = knowledgeService.showKnowledge(knowledgeId, userId);
			view.addObject("topic", topic);
		} catch (Exception e) {
			logger.error("{}话题加载出错", e);
			view.setViewName("redirect:"+urlConfig.getError_404());
		}
		return view;
	}
	
	@RequestMapping("/prePublicKnowledge")
	public ModelAndView prePublicknowledge(){
		ModelAndView view = new ModelAndView("/page/knowledge/publicKnowledge");
		return view;
	}

	@RequestMapping("/loadCategories")
	public OutResponse<List<Category>> loadCategories(){
		OutResponse<List<Category>> outResponse = new OutResponse<>();
		try {
			outResponse.setData(categoryCache.getKnowledgeCategories());
			outResponse.setCode(CodeEnum.SUCCESS);
			return outResponse;
		} catch (Exception e) {
			outResponse.setMsg("加载分类出错");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("{}加载分类出错",e);
		}
		return outResponse;
	}

	@RequestMapping("/publicKnowledge")
	public OutResponse<String> publicKnowledge(HttpSession session, Knowledge knowledge, Attachment attachment){
		OutResponse<String> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		try {
			setUserBaseInfo(Knowledge.class, knowledge, session);
			knowledge.setKnowledgeId(UUIDUtil.getUUID());
			knowledgeService.addKnowledge(knowledge, attachment);
			outResponse.setCode(CodeEnum.SUCCESS);
			outResponse.setData(knowledge.getKnowledgeId());
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			logger.error("{}投稿失败", sessionUser.getUserName());
		} catch (Exception e) {
			outResponse.setMsg("服务器出错,投稿失败");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("{}投稿失败", sessionUser.getUserName());
		}
		return outResponse;
	}
	
	
}

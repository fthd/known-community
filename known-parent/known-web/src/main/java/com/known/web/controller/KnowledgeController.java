package com.known.web.controller;

import com.known.cache.CategoryCache;
import com.known.common.enums.ResponseCode;
import com.known.common.model.Attachment;
import com.known.common.model.Category;
import com.known.common.model.Knowledge;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.KnowledgeQuery;
import com.known.service.AttachmentService;
import com.known.service.CategoryService;
import com.known.service.KnowledgeService;
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
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(BbsController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private CategoryCache categoryCache;
	
	@RequestMapping
	public ModelAndView knowledge(HttpSession session, KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = this.knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping(value = "/pCategoryId/{pCategoryId}", method= RequestMethod.GET)
	public ModelAndView pCategoryId(@PathVariable Integer pCategoryId, KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = this.knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("KnwoledgeTitleCategory", CategoryCache.getCategoryById(pCategoryId));
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping(value = "/categoryId/{categoryId}", method= RequestMethod.GET)
	public ModelAndView categoryId(@PathVariable Integer categoryId, KnowledgeQuery knowledgeQuery){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge");
		PageResult<Knowledge> pageResult = this.knowledgeService.findKnowledgeByPage(knowledgeQuery);
		view.addObject("categories", categoryCache.getKnowledgeCategories());
		view.addObject("KnwoledgeTitleCategory", CategoryCache.getCategoryById(categoryId));
		view.addObject("result", pageResult);
		return view;
	}
	
	@RequestMapping("/{knowledgeId}")
	public ModelAndView knowledgeDetail(@PathVariable Integer knowledgeId, HttpSession session){
		ModelAndView view = new ModelAndView("/page/knowledge/knowledge_detail");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		Integer userId = null;
		try {
			userId = sessionUser==null ? null : sessionUser.getUserid();
			Knowledge topic = this.knowledgeService.showKnowledge(knowledgeId, userId);
			view.addObject("topic", topic);
		} catch (Exception e) {
			logger.error("{}话题加载出错", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
		}
		return view;
	}
	
	@RequestMapping("/prePublicKnowledge")
	public ModelAndView prePublicknowledge(HttpSession session){
		ModelAndView view = new ModelAndView("/page/knowledge/publicKnowledge");
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("loadCategories")
	public AjaxResponse<List<Category>> loadCategories(){
		AjaxResponse<List<Category>> ajaxResponse = new AjaxResponse<List<Category>>();
		try {
			ajaxResponse.setData(this.categoryCache.getKnowledgeCategories());
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
	@RequestMapping("publicKnowledge")
	public AjaxResponse<Integer> publicKnowledge(HttpSession session, Knowledge knowledge, Attachment attachment){
		AjaxResponse<Integer> ajaxResponse = new AjaxResponse<Integer>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		try {
			this.setUserBaseInfo(Knowledge.class, knowledge, session);
			this.knowledgeService.addKnowledge(knowledge, attachment);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(knowledge.getTopicId());
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}投稿失败", sessionUser.getUserName());
		} catch (Exception e) {
			ajaxResponse.setErrorMsg("服务器出错,投稿失败");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}投稿失败", sessionUser.getUserName());
		}
		return ajaxResponse;
	}
	
	
}

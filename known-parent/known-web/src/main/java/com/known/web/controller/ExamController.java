package com.known.web.controller;

import com.known.common.enums.ExamChooseType;
import com.known.common.enums.ResponseCode;
import com.known.common.model.Category;
import com.known.common.model.Exam;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CategoryQuery;
import com.known.manager.query.ExamQuery;
import com.known.service.CategoryService;
import com.known.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("exam")
public class ExamController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(ExamController.class);
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ExamService examService;
	
	@RequestMapping
	public ModelAndView exam(HttpSession session){
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInExam(Constants.Y);
		List<Category> categoryList = this.categoryService.findCategoryList(categoryQuery);
		ModelAndView view = new ModelAndView("/page/exam/exam");
		view.addObject("categoryList", categoryList);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadExamiers")
	public AjaxResponse<PageResult<Exam>> loadExamiers(ExamQuery examQuery){
		AjaxResponse<PageResult<Exam>> ajaxResponse = new AjaxResponse<PageResult<Exam>>();
		ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		try{
			PageResult<Exam> pageResult = this.examService.findExamUsers(examQuery);
			ajaxResponse.setData(pageResult);
		}catch(Exception e){
			logger.error("加载出题人出错", e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			ajaxResponse.setErrorMsg("加载出题人失败");
		}
		return ajaxResponse;
	}
	
	@RequestMapping("addExam")
	public ModelAndView addExam(HttpSession session){
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInExam(Constants.Y);
		List<Category> categoryList = this.categoryService.findCategoryList(categoryQuery);
		ModelAndView view = new ModelAndView("/page/exam/addExam");
		view.addObject("categoryList", categoryList);
		view.addObject("examChooseType", ExamChooseType.values());
		return view;
	}
	
	@ResponseBody
	@RequestMapping("postExam")
	public AjaxResponse<Object> postExam(HttpSession session, Exam exam, String[] answer, Integer[] rightAnswer){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			this.setUserBaseInfo(Exam.class, exam, session);
			this.examService.saveExam(exam, answer, rightAnswer);
			ajaxResponse.setData(exam);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			logger.error("{}出题错误", exam.getUserName());
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
		}catch(Exception e){
			logger.error("{}出题错误", exam.getUserName());
			ajaxResponse.setErrorMsg("出题出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("preDoExam")
	public ModelAndView preDoExam(HttpSession session, Integer categoryId){
		ModelAndView view = new ModelAndView("/page/exam/doExam");
		view.addObject("categoryId", categoryId);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadAllExam")
	public AjaxResponse<List<Exam>> loadAllExam(HttpSession session, Integer categoryId){
		AjaxResponse<List<Exam>> ajaxResponse = new AjaxResponse<List<Exam>>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			List<Exam> list = this.examService.findExamListRand(categoryId);
			ajaxResponse.setData(list);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			ajaxResponse.setErrorMsg("加载试题出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}加载试题出错",sessionUser.getUserName());
		}	
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("doMark")
	public AjaxResponse<List<Exam>> doMark(HttpSession session, String examIds, String rightAnswers){
		AjaxResponse<List<Exam>> ajaxResponse = new AjaxResponse<List<Exam>>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			List<Exam> list = this.examService.doMark(examIds, rightAnswers);
			ajaxResponse.setData(list);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		}catch (BussinessException e) {
			logger.error("{}改卷错误", sessionUser.getUserName());
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
		} 
		catch (Exception e) {
			ajaxResponse.setErrorMsg("改卷出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
			logger.error("{}改卷出错",sessionUser.getUserName());
		}	
		return ajaxResponse;
	}
}

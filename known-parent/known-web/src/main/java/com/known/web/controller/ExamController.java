package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.enums.ExamChooseType;
import com.known.common.enums.Code;
import com.known.common.model.Category;
import com.known.common.model.Exam;
import com.known.common.model.SessionUser;
import com.known.common.utils.Constants;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.CategoryQuery;
import com.known.manager.query.ExamQuery;
import com.known.service.CategoryService;
import com.known.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(ExamController.class);
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ExamService examService;

	@Resource
	private UserConfig userConfig;
	
	@RequestMapping("/exam")
	public ModelAndView exam(HttpSession session){
		CategoryQuery categoryQuery = new CategoryQuery();
		categoryQuery.setShowInExam(Constants.Y);
		List<Category> categoryList = this.categoryService.findCategoryList(categoryQuery);
		ModelAndView view = new ModelAndView("/page/exam/exam");
		view.addObject("categoryList", categoryList);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/loadExamiers")
	public OutResponse<PageResult<Exam>> loadExamiers(ExamQuery examQuery){
		OutResponse<PageResult<Exam>> outResponse = new OutResponse<PageResult<Exam>>();
		outResponse.setCode(Code.SUCCESS);
		try{
			PageResult<Exam> pageResult = this.examService.findExamUsers(examQuery);
			outResponse.setData(pageResult);
		}catch(Exception e){
			logger.error("加载出题人出错", e.getLocalizedMessage());
			outResponse.setCode(Code.SERVERERROR);
			outResponse.setMsg("加载出题人失败");
		}
		return outResponse;
	}
	
	@RequestMapping("/addExam")
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
	@RequestMapping("/postExam")
	public OutResponse<Object> postExam(HttpSession session, Exam exam, String[] answer, Integer[] rightAnswer){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			this.setUserBaseInfo(Exam.class, exam, session);
			this.examService.saveExam(exam, answer, rightAnswer);
			outResponse.setData(exam);
			outResponse.setCode(Code.SUCCESS);
		} catch (BussinessException e) {
			logger.error("{}出题错误", exam.getUserName());
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}catch(Exception e){
			logger.error("{}出题错误", exam.getUserName());
			outResponse.setMsg("出题出错");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("/preDoExam")
	public ModelAndView preDoExam(HttpSession session, Integer categoryId){
		ModelAndView view = new ModelAndView("/page/exam/doExam");
		view.addObject("categoryId", categoryId);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/loadAllExam")
	public OutResponse<List<Exam>> loadAllExam(HttpSession session, Integer categoryId){
		OutResponse<List<Exam>> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			List<Exam> list = this.examService.findExamListRand(categoryId);
			outResponse.setData(list);
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			outResponse.setMsg("加载试题出错");
			outResponse.setCode(Code.SERVERERROR);
			logger.error("{}加载试题出错",sessionUser.getUserName());
		}	
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("/doMark")
	public OutResponse<List<Exam>> doMark(HttpSession session, String examIds, String rightAnswers){
		OutResponse<List<Exam>> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			List<Exam> list = this.examService.doMark(examIds, rightAnswers);
			outResponse.setData(list);
			outResponse.setCode(Code.SUCCESS);
		}catch (BussinessException e) {
			logger.error("{}改卷错误", sessionUser.getUserName());
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		} 
		catch (Exception e) {
			outResponse.setMsg("改卷出错");
			outResponse.setCode(Code.SERVERERROR);
			logger.error("{}改卷出错",sessionUser.getUserName());
		}	
		return outResponse;
	}
}

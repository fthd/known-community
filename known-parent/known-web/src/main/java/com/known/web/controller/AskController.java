package com.known.web.controller;

import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.SolveEnum;
import com.known.common.model.Ask;
import com.known.common.model.SessionUser;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtil;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.AskQuery;
import com.known.service.AskService;
import com.known.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 问答
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-24 00:07
 */
@Controller
@RequestMapping("/ask")
public class AskController extends BaseController {

	@Autowired
	private AskService askService;
	
	@Autowired
	private UserService userService;

	@Resource
	private UserConfig userConfig;

	@Resource
	private UrlConfig urlConfig;
	
	
	private Logger logger = LoggerFactory.getLogger(AskController.class);

	
	@RequestMapping("/ask")
	public ModelAndView ask(HttpSession session, AskQuery askQuery){
		ModelAndView view = new ModelAndView("/page/ask/ask");
		if(askQuery.getSolveType() == null){
			askQuery.setSolveType(SolveEnum.WAIT_SOLVE);
		}
		PageResult<Ask> result = this.askService.findAskByPage(askQuery);
		view.addObject("result", result);
		view.addObject("haveSolved", askQuery.getSolveType().getType());
		AskQuery todayAskQuery = new AskQuery();
		view.addObject("totalAsk", this.askService.findCount(todayAskQuery));
		todayAskQuery.setSolveType(SolveEnum.SOLVED);
		view.addObject("totalSolved", this.askService.findCount(todayAskQuery));	
		todayAskQuery = new AskQuery();
		Date curDate = new Date();
		todayAskQuery.setStartDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		todayAskQuery.setEndDate(DateUtil.format(curDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()));
		view.addObject("todayAskCount", this.askService.findCount(todayAskQuery));
		todayAskQuery.setSolveType(SolveEnum.SOLVED);
		view.addObject("todaySolvedCount", this.askService.findCount(todayAskQuery));
		view.addObject("topUsers", this.askService.findTopUsers());
		return view;
	}
	
	@RequestMapping("/prePublicAsk")
	public ModelAndView prePublicAsk(HttpSession session){
		ModelAndView view = new ModelAndView("/page/ask/publicAsk");
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			 view = new ModelAndView("/page/login");
			return view;
		}
		view.addObject("myCount", userService.findUserByUserid(sessionUser.getUserid()).getMark());
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/publicAsk")
	public OutResponse<Integer> publicAsk(HttpSession session, Ask ask){
		OutResponse<Integer> outResponse = new OutResponse<>();
		this.setUserBaseInfo(Ask.class, ask, session);
		try {
			this.askService.addAsk(ask);
			outResponse.setData(ask.getAskId());
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			logger.error("{}提问出错{}", ask.getUserName(), e);
		}catch (Exception e) {
			outResponse.setMsg("服务器出错，提问失败");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("{}提问出错{}", ask.getUserName(), e);
		}
		return outResponse;
	}
	
	@RequestMapping(value="/{askId}", method= RequestMethod.GET)
	public ModelAndView askDetail(@PathVariable Integer askId, HttpSession session){
		ModelAndView view = new ModelAndView("/page/ask/ask_detail");
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		try {
			String flag = (String) session.getAttribute(sessionUser==null?"Y":sessionUser.getUserid()+"_ready");
			view.addObject("ask", this.askService.showAsk(askId,flag));
			if(StringUtil.isEmpty(flag)){
				session.setAttribute(sessionUser==null?"Y":sessionUser.getUserid()+"_ready","flag");
			}
		} catch (BussinessException e) {
			logger.error("{}问题加载出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
			view.setViewName("redirect:" + urlConfig.getError_404());
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/acceptAnswer")
	public OutResponse<Object> acceptAnswer(HttpSession session, Ask ask){
		OutResponse<Object> outResponse = new OutResponse<>();
		this.setUserBaseInfo(Ask.class, ask, session);
		try {
			this.askService.setBestAnswer(ask.getBestAnswerId(), ask.getAskId(), ask.getUserId());
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			logger.error("采纳答案出错{}", e);
		}catch (Exception e) {
			outResponse.setMsg("服务器出错了");
			outResponse.setCode(CodeEnum.SERVERERROR);
			logger.error("采纳答案出错{}", e);
		}
		return outResponse;
	}
}

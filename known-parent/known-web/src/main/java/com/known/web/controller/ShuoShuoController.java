package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.enums.Code;
import com.known.common.model.SessionUser;
import com.known.common.model.ShuoShuo;
import com.known.common.model.ShuoShuoComment;
import com.known.common.model.ShuoShuoLike;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.ShuoShuoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/shuoShuo")
public class ShuoShuoController extends BaseController {

	@Autowired
	private ShuoShuoService shuoShuoService;


	private Logger logger = LoggerFactory.getLogger(ShuoShuoController.class);

	@Resource
	private UserConfig userConfig;

	@RequestMapping("/shuoShuo")
	public ModelAndView ShuoShuo(){
		ModelAndView view = new ModelAndView("/page/shuoshuo");
		return view;
	}


	/**
	 * 发布说说
	 * @param session
	 * @param shuoShuo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/publicShuoShuo")
	public OutResponse<Object> publicShuoShuo(HttpSession session, ShuoShuo shuoShuo){

		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());

		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}

		try {
			this.setUserBaseInfo(ShuoShuo.class, shuoShuo, session);
			this.shuoShuoService.addShuoShuo(shuoShuo);
			outResponse.setCode(Code.SUCCESS);
			outResponse.setData(shuoShuo);
		} catch (BussinessException e) {
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}说说发表出错", shuoShuo.getUserName());
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("/publicShuoShuoComment")
	public OutResponse<Object> publicShuoShuo(HttpSession session, ShuoShuoComment shuoShuoComment){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			this.setUserBaseInfo(ShuoShuoComment.class, shuoShuoComment, session);
			this.shuoShuoService.addShuoShuoComment(shuoShuoComment);
			outResponse.setCode(Code.SUCCESS);
			outResponse.setData(shuoShuoComment);
		} catch (BussinessException e) {
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", shuoShuoComment.getUserName());
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("/doShuoShuoLike")
	public OutResponse<Object> doShuoShuoLike(HttpSession session, ShuoShuoLike shuoShuoLike){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		this.setUserBaseInfo(ShuoShuoLike.class, shuoShuoLike, session);
		try {
			this.shuoShuoService.doShuoShuoLike(shuoShuoLike);
			outResponse.setCode(Code.SUCCESS);
			outResponse.setData(shuoShuoLike);
		} catch (BussinessException e) {
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}点赞出错", shuoShuoLike.getUserName());
		}
		return outResponse;
	}
}

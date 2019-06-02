package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
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
	@RequestMapping("/publicShuoShuo")
	public OutResponse<Object> publicShuoShuo(HttpSession session, ShuoShuo shuoShuo){

		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());

		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}

		try {
			setUserBaseInfo(ShuoShuo.class, shuoShuo, session);
			shuoShuoService.addShuoShuo(shuoShuo);
			outResponse.setCode(CodeEnum.SUCCESS);
			outResponse.setData(shuoShuo);
		} catch (BussinessException e) {
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}说说发表出错", shuoShuo.getUserName());
		}
		return outResponse;
	}

	/**
	 * 说说评论
	 * @param session
	 * @param shuoShuoComment
	 * @return
	 */
	@RequestMapping("/publicShuoShuoComment")
	public OutResponse<Object> publicShuoShuo(HttpSession session, ShuoShuoComment shuoShuoComment){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			setUserBaseInfo(ShuoShuoComment.class, shuoShuoComment, session);
			shuoShuoService.addShuoShuoComment(shuoShuoComment);
			outResponse.setCode(CodeEnum.SUCCESS);
			outResponse.setData(shuoShuoComment);
		} catch (BussinessException e) {
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}评论出错", shuoShuoComment.getUserName());
		}
		return outResponse;
	}

	/**
	 * 说说点赞
	 * @param session
	 * @param shuoShuoLike
	 * @return
	 */
	@RequestMapping("/doShuoShuoLike")
	public OutResponse<Object> doShuoShuoLike(HttpSession session, ShuoShuoLike shuoShuoLike){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		setUserBaseInfo(ShuoShuoLike.class, shuoShuoLike, session);
		try {
			shuoShuoService.doShuoShuoLike(shuoShuoLike);
			outResponse.setCode(CodeEnum.SUCCESS);
			outResponse.setData(shuoShuoLike);
		} catch (BussinessException e) {
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg(e.getLocalizedMessage());
			logger.error("{}点赞出错", shuoShuoLike.getUserName());
		}
		return outResponse;
	}
}

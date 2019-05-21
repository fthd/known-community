package com.known.web.controller;

import com.known.common.enums.ResponseCode;
import com.known.common.model.ShuoShuo;
import com.known.common.model.ShuoShuoComment;
import com.known.common.model.ShuoShuoLike;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.ShuoShuoQuery;
import com.known.service.ShuoShuoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ShuoShuoController extends BaseController {
	@Autowired
	private ShuoShuoService shuoShuoService;
	private Logger logger = LoggerFactory.getLogger(ShuoShuoController.class);
	@RequestMapping("shuoshuo")
	public ModelAndView ShuoShuo(){
		ModelAndView view = new ModelAndView("page/shuoshuo");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadShuoShuos")
	public AjaxResponse<Object> loadShuoShuos(HttpSession session, int pageNum){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
		shuoShuoQuery.setPageNum(pageNum);
		PageResult<ShuoShuo> pageResult = this.shuoShuoService.findShuoShuoList(shuoShuoQuery);
		ajaxResponse.setData(pageResult);
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("publicShuoShuo")
	public AjaxResponse<Object> publicShuoShuo(HttpSession session, ShuoShuo shuoShuo){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			if(session.getAttribute("shuoShuoTimeOut") != null){
				Date date = (Date) session.getAttribute("shuoShuoTimeOut");
				Date now = new Date();
				long interval =(now.getTime() - date.getTime())/1000;
				if(interval <= 60){
					ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
					ajaxResponse.setErrorMsg("一分钟之内不能频繁发说说,休息一下再发.....");
					return ajaxResponse;
				}else{
					session.removeAttribute("shuoShuoTimeOut");
				}
			}else{
				session.setAttribute("shuoShuoTimeOut",new Date());
			}
			this.setUserBaseInfo(ShuoShuo.class, shuoShuo, session);
			this.shuoShuoService.addShuoShuo(shuoShuo);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
			ajaxResponse.setData(shuoShuo);
		} catch (BussinessException e) {
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			logger.error("{}说说发表出错", shuoShuo.getUserName());
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
}

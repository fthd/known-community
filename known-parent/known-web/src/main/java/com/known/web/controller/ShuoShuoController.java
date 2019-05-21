package com.known.web.controller;

import com.known.common.enums.Code;
import com.known.common.model.ShuoShuo;
import com.known.common.model.ShuoShuoComment;
import com.known.common.model.ShuoShuoLike;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.ShuoShuoQuery;
import com.known.service.ShuoShuoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/shuoShuo")
public class ShuoShuoController extends BaseController {

	@Autowired
	private ShuoShuoService shuoShuoService;

	private Logger logger = LoggerFactory.getLogger(ShuoShuoController.class);

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;

	@RequestMapping("/shuoshuo")
	public ModelAndView ShuoShuo(){
		ModelAndView view = new ModelAndView("/page/shuoshuo");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/loadShuoShuos")
	public OutResponse<Object> loadShuoShuos(HttpSession session, int pageNum){
		OutResponse<Object> outResponse = new OutResponse<>();
		ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
		shuoShuoQuery.setPageNum(pageNum);
		PageResult<ShuoShuo> pageResult = this.shuoShuoService.findShuoShuoList(shuoShuoQuery);
		outResponse.setData(pageResult);
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("/publicShuoShuo")
	public OutResponse<Object> publicShuoShuo(HttpSession session, ShuoShuo shuoShuo){
		OutResponse<Object> outResponse = new OutResponse<>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(SESSION_USER_KEY);
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			if(session.getAttribute("shuoShuoTimeOut") != null){
				Date date = (Date) session.getAttribute("shuoShuoTimeOut");
				Date now = new Date();
				long interval =(now.getTime() - date.getTime())/1000;
				if(interval <= 60){
					outResponse.setCode(Code.BUSSINESSERROR);
					outResponse.setMsg("一分钟之内不能频繁发说说,休息一下再发.....");
					return outResponse;
				}else{
					session.removeAttribute("shuoShuoTimeOut");
				}
			}else{
				session.setAttribute("shuoShuoTimeOut",new Date());
			}
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
		UserRedis sessionUser = (UserRedis) session.getAttribute(SESSION_USER_KEY);
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
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
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

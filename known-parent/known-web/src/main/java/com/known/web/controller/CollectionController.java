package com.known.web.controller;

import com.known.common.enums.Code;
import com.known.common.model.Collection;
import com.known.common.model.UserRedis;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/collention")
public class CollectionController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CollectionController.class);
	
	@Autowired
	private CollectionService collectionService;

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;


	@RequestMapping("/doCollection")
	public OutResponse<Object> doCollection(HttpSession session, Collection collection){
		OutResponse<Object> outResponse = new OutResponse<>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(SESSION_USER_KEY);
		if(sessionUser==null){
			outResponse.setCode(Code.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			this.collectionService.addCollection(collection);
			outResponse.setCode(Code.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
			outResponse.setMsg("服务器出错");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
}

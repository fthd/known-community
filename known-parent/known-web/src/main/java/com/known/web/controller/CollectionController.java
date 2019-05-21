package com.known.web.controller;

import com.known.common.enums.ResponseCode;
import com.known.common.model.Collection;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.exception.BussinessException;
import com.known.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CollectionController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CollectionController.class);
	
	@Autowired
	private CollectionService collectionService;
	
	@ResponseBody
	@RequestMapping("doCollection")
	public AjaxResponse<Object> doCollection(HttpSession session, Collection collection){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			this.collectionService.addCollection(collection);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("{}收藏出错{}", sessionUser.getUserName(), e.getLocalizedMessage());
			ajaxResponse.setErrorMsg("服务器出错");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
}

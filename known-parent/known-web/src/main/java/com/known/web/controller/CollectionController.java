package com.known.web.controller;

import com.known.common.config.UserConfig;
import com.known.common.enums.Code;
import com.known.common.model.Collection;
import com.known.common.model.SessionUser;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 收藏
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-24 00:09
 */
@Controller
@RequestMapping("/collention")
public class CollectionController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CollectionController.class);
	
	@Autowired
	private CollectionService collectionService;

	@Resource
	private UserConfig userConfig;


	@RequestMapping("/doCollection")
	public OutResponse<Object> doCollection(HttpSession session, Collection collection){
		OutResponse<Object> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
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

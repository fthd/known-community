package com.known.web.controller;

import com.known.common.enums.Code;
import com.known.common.model.SignInfo;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.SignInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户签到
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-22 00:07
 */
@RestController
@RequestMapping("/signIn")
public class SignInController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Autowired
	private SignInService signInService;

	@Value("${SESSION_USER_KEY}")
	private String SESSION_USER_KEY;

	/**
	 * 加载签到信息
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadSignInfo")
	public OutResponse<Object> loadSignInfo(HttpSession session){

		OutResponse<Object> outResponse = new OutResponse<>();
		Integer userid = this.getUserid(session);
		SignInfo signInfo = this.signInService.findSignInfoByUserid(userid);
		outResponse.setData(signInfo);
		return outResponse;
	}

	/**
	 * 进行签到
	 * @param session
	 * @return
	 */
	@RequestMapping("/signIn")
	public OutResponse<Object> signIn(HttpSession session){
		OutResponse<Object> outResponse = new OutResponse<>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(SESSION_USER_KEY);
		try {
			this.signInService.doSignIn(sessionUser);
			outResponse.setCode(Code.SUCCESS);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
			logger.error("用户{}签到失败", sessionUser.getUserName());
		}catch (Exception e) {
			outResponse.setMsg(Code.SERVERERROR.getDesc());
			outResponse.setCode(Code.SERVERERROR);
			logger.error("用户签到失败,用户名",sessionUser.getUserName());
		}
		return outResponse;
	}

}

package com.known.service;

import com.known.common.model.SignIn;
import com.known.common.model.SignInfo;
import com.known.common.model.UserRedis;
import com.known.exception.BussinessException;

public interface SignInService {
	 SignInfo findSignInfoByUserid(Integer userid);
	
	SignIn doSignIn(UserRedis sessionUser)throws BussinessException;
}

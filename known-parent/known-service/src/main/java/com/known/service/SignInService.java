package com.known.service;

import com.known.common.model.SignIn;
import com.known.common.model.SignInfo;
import com.known.common.model.SessionUser;
import com.known.exception.BussinessException;

public interface SignInService {
	 SignInfo findSignInfoByUserid(String userid);
	
	SignIn doSignIn(SessionUser sessionUser)throws BussinessException;
}

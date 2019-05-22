package com.known.service_impl;

import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.MarkEnum;
import com.known.common.model.SignIn;
import com.known.common.model.SignInfo;
import com.known.common.model.User;
import com.known.common.model.SessionUser;
import com.known.common.utils.Constants;
import com.known.common.utils.DateUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.SignInMapper;
import com.known.manager.query.SignInQuery;
import com.known.service.SignInService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class SignInServiceImpl implements SignInService {

	@Autowired
	private SignInMapper<SignIn, SignInQuery> signInMapper;

	@Autowired
	private UserService userService;


	public SignInfo findSignInfoByUserid(Integer userid) {
		SignInQuery signInQuery = new SignInQuery();
		SignInfo signInfo = new SignInfo();
		Date curDate = new Date();
		signInQuery.setCurDate(curDate);
		//查询当前日期签到了多少人
		signInfo.setTodaySignInCount(this.signInMapper.selectCount(signInQuery));
		if(null == userid){
			//没登录,默认没签到，签到天数为0
			signInfo.setHaveSignInToday(Boolean.FALSE);
			signInfo.setUserSignInCount(0);
		}
		else{
			signInQuery = new SignInQuery();
			User user = this.userService.findUserByUserid(userid);
			signInfo.setMark(user.getMark());
			signInQuery.setUserid(userid);
			//查询该用户总共签到了多少天
			int userSignInCount = this.signInMapper.selectCount(signInQuery);
			signInfo.setUserSignInCount(userSignInCount);
			signInQuery.setCurDate(curDate);
			int todayIsSignIn = this.signInMapper.selectCount(signInQuery);
			if(todayIsSignIn == 0){
				//查询该用户当天是否签到
				signInfo.setHaveSignInToday(Boolean.FALSE);
			}
			else{
				signInfo.setHaveSignInToday(Boolean.TRUE);
			}
			signInfo.setCurDate(DateUtil.format(curDate, DateTimePatternEnum.MM_POINT_DD.getPattern()));
			signInfo.setCurYear(DateUtil.format(curDate, DateTimePatternEnum.YYYY.getPattern()));
			signInfo.setCurMonth(DateUtil.format(curDate, DateTimePatternEnum.MM.getPattern()));
			signInfo.setCurDay(DateUtil.format(curDate, DateTimePatternEnum.dd.getPattern()));
			
		}
		return signInfo;
	}
	//签到是事物，配置开启新事物，并且只有在发生BussinessException才回滚
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
	public SignIn doSignIn(SessionUser sessionUser) throws BussinessException {
		Integer userid = sessionUser.getUserid();
		Date curDate = new Date();
		SignInQuery signInQuery = new SignInQuery();
		signInQuery.setUserid(userid);
		signInQuery.setCurDate(curDate);
		int todayUserSignInCount = this.signInMapper.selectCount(signInQuery);
		if(todayUserSignInCount > 0){
			throw new BussinessException("今日已经签到");
		}
		SignIn signIn = new SignIn();
		signIn.setUserid(sessionUser.getUserid());
			signIn.setUserIcon(sessionUser.getUserIcon());
		signIn.setUserName(sessionUser.getUserName());
		signIn.setSignDate(curDate);
		signIn.setSignTime(curDate);
		this.signInMapper.insert(signIn);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		//把当前日期置空，以便查询区间日期是否连续签到
		signInQuery.setCurDate(null);
		
		signInQuery.setStartDate(calendar.getTime());
		signInQuery.setEndDate(curDate);
		int count = this.signInMapper.selectCount(signInQuery);
		int mark;
		if(count >= Constants.CONTINUOUS_SIGNIN){
			mark = MarkEnum.MARK_SIGN_CONTINUE.getMark();
		}
		else{
			mark = MarkEnum.MARK_SIGNIN.getMark();
		}
		this.userService.addMark(mark, userid);
		return signIn;
	}

}

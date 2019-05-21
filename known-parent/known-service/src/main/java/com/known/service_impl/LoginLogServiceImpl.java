package com.known.service_impl;


import com.known.common.model.LoginLog;
import com.known.manager.mapper.LoginLogMapper;
import com.known.manager.query.LoginLogQuery;
import com.known.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {
	
	@Autowired
	private LoginLogMapper<LoginLog, LoginLogQuery> loginLogMapper;

	@Override
	public void addLoginLog(LoginLog loginLog) {
		loginLogMapper.insert(loginLog);
	}

	@Override
	public List<LoginLog> findLoginLog() {
		LoginLogQuery loginLogQuery = new LoginLogQuery();
		return loginLogMapper.selectList(loginLogQuery);
	}

	@Override
	public List<LoginLog> findLoginLogGroupByIp() {
		LoginLogQuery loginLogQuery = new LoginLogQuery();
		return loginLogMapper.selectListGroupByIp(loginLogQuery);
	}

}

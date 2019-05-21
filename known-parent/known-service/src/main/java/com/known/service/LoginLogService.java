package com.known.service;

import com.known.common.model.LoginLog;

import java.util.List;

public interface LoginLogService {
	
	void addLoginLog(LoginLog loginLog);
	
	List<LoginLog> findLoginLog();
	
	List<LoginLog> findLoginLogGroupByIp();
}

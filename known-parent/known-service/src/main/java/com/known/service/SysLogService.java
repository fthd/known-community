package com.known.service;


import com.known.common.model.SysLog;

import java.util.List;

public interface SysLogService {
	
	void addSysLog(SysLog sysLog);
	
	List<SysLog> findSysLogList();
}

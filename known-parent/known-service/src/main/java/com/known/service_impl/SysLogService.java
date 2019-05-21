package com.known.service_impl;

import com.known.common.model.SysLog;
import com.known.manager.mapper.SysLogMapper;
import com.known.manager.query.SysLogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogService implements com.known.service.SysLogService {
	
	@Autowired
	private SysLogMapper<SysLog, SysLogQuery> sysLogMapper;
	
	@Override
	public void addSysLog(SysLog sysLog) {
		sysLogMapper.insert(sysLog);
	}

	@Override
	public List<SysLog> findSysLogList() {
		SysLogQuery sysLogQuery = new SysLogQuery();
		return sysLogMapper.selectList(sysLogQuery);
	}

}

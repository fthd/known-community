package com.known.quartz;

import com.known.common.utils.SpringContextUtil;
import com.known.service.IStatisticalDataService;

public class StatisticsTask {

	private IStatisticalDataService iStatisticalDataService = (IStatisticalDataService) SpringContextUtil.getBean("statisticalDataServiceImpl");
	
	public void statisticsData(){
		iStatisticalDataService.caculateData();
	}
}

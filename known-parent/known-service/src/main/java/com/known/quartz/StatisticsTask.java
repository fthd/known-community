package com.known.quartz;

import com.known.common.utils.SpringContextUtil;
import com.known.service.IStatisticalDataService;

/**
 * 任务执行
 * 统计所有报表数据
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-05 13:00
 */
public class StatisticsTask {

	// 手动获取统计service
	private IStatisticalDataService iStatisticalDataService = (IStatisticalDataService) SpringContextUtil.getBean("statisticalDataServiceImpl");

	public void statisticsData(){
		iStatisticalDataService.caculateData();
	}
}

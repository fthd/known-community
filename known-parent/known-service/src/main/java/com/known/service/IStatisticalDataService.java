package com.known.service;

import com.known.common.echart.Echart;
import com.known.common.model.Statistics;
import com.known.exception.BussinessException;

import java.util.List;

public interface IStatisticalDataService {
	
	void caculateData();
	
	List<Statistics> findStatistics() throws BussinessException;
	
	List<Echart> findEcharts() throws BussinessException;
}

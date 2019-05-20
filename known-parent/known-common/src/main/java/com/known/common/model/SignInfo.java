package com.known.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInfo {
	//今天是否已经签到
	private boolean haveSignInToday;
	
	//用户签到了的天数总和
	private Integer userSignInCount = 0;
	
	//今日签到人数
	private Integer todaySignInCount;
	
	//当前日期
	private String curDate;
	
	//当前年份
	private String curYear;
	
	//当前月份
	private String curMonth;
	
	//当前几号
	private String curDay;

	//用户积分
	private Integer mark = 0;

}

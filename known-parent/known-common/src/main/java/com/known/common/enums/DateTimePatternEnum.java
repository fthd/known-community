package com.known.common.enums;

public enum DateTimePatternEnum {
	YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"), 
	YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"), 
	YYYYMM("yyyyMM"), 
	YYYY("yyyy"), 
	MM("MM"), 
	dd("dd"),
	MM_POINT_DD("MM.dd"), 
	YYYY_MM_DD("yyyy-MM-dd");
	private String pattern;
	
	private DateTimePatternEnum(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
}

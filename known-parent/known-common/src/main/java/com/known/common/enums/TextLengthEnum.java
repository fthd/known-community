package com.known.common.enums;

public enum TextLengthEnum {
	TEXT_50_LENGTH(50, "话题类型名称长度"),
	TEXT_100_LENGTH(100, "话题名称长度"),
	TEXT_200_LENGTH(200, "标题长度"), 
	TEXT_300_LENGTH(300, "收藏话题长度"),
	TEXT_500_LENGTH(500, "二级评论长度"), 
	TEXT(65535, "MYSQL TEXT的长度"), 
	MEDIUMTEXT(16777215, "MYSQL MEDIUMTEXT的长度"),
	LONGTEXT(4294967295l, "MYSQL LONGTEXT的长度");
	
	private long length;
	private String desc;
	
	
	private TextLengthEnum(long length, String desc) {
		this.length = length;
	}

	public long getLength() {
		return length;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

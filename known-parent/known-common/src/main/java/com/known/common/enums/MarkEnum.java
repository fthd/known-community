package com.known.common.enums;

public enum MarkEnum {
	MARK_SIGNIN(6,"普通签到"), 
	MARK_SIGN_CONTINUE(8,"签到7天以上"),
	MARK_SHUOSHUO(2,"发说说"),
	MARK_COMMENT(1,"评论"),
	MARK_TOPIC(1,"发布话题");

	private int mark;
	private String desc;
	private MarkEnum(int mark, String desc) {
		this.mark = mark;
		this.desc = desc;
	}
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

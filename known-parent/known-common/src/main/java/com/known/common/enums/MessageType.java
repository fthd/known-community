package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum MessageType {
	AT_ARTICLE_MESSAGE(1, "话题中@用户消息"), COMMENT_MESSAGE(2, "评论消息"),
	ADOPT_ANSWER(3, "采纳答案消息"), SYSTEM_WARN(4, "系统警告消息"), SYSTEM_MARK(5, "系统积分消息");
	
	private Integer type;
	
	private String desc;

	private MessageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

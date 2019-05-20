package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TopicType {
	NORMAL(0, "普通话题"), VOTE(1, "投票话题");
	private Integer type;
	private String desc;
	
	private TopicType(Integer type, String desc) {
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
	
	public static TopicType getTopicTypeByValue(Integer value){
		if(value == null){
			return null;
		}
		for(TopicType topicType : TopicType.values()){
			if(topicType.getType() == value){
				return topicType;
			}
		}
		return null;
	}
}

package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum MessageStatusEnum {
	
	UNREAD(0,"未读"), READED(1, "已读");
	
	private Integer type;
	
	private String desc;

	private MessageStatusEnum(Integer type, String desc) {
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
	
	public static MessageStatusEnum getMessageStatusByType(Integer type){
		if(type == null){
			return null;
		}
		for(MessageStatusEnum ms : MessageStatusEnum.values()){
			if(ms.getType().intValue() == type){
				return ms;
			}
		}
		return null;
	}
	
}

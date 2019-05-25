package com.known.common.enums;


import com.known.common.utils.StringUtil;

public enum FileTopicTypeEnum {
	TOPIC("T", "话题附件"), KNOWLEDGE("K", "知识库附件");
	private String type;
	private String desc;

	private FileTopicTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static FileTopicTypeEnum getFileTopicTypeByType(String type){
		if(StringUtil.isEmpty(type)){
			return null;
		}
		for(FileTopicTypeEnum fileTopicType : FileTopicTypeEnum.values()){
			if(fileTopicType.getType().equals(type)){
				return fileTopicType;
			}
		}
		return null;
	}
}

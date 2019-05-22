package com.known.common.enums;


import com.known.common.utils.StringUtil;

public enum FileTopicType {
	TOPIC("T", "论坛附件"), KNOWLEDGE("K", "论坛附件"),  BLOG("B", "话题附件");
	private String type;
	private String desc;

	private FileTopicType(String type, String desc) {
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
	
	public static FileTopicType getFileTopicTypeByType(String type){
		if(StringUtil.isEmpty(type)){
			return null;
		}
		for(FileTopicType fileTopicType : FileTopicType.values()){
			if(fileTopicType.getType().equals(type)){
				return fileTopicType;
			}
		}
		return null;
	}
}

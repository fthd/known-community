package com.known.common.enums;


import com.known.common.utils.StringUtil;

public enum FileArticleTypeEnum {
	TOPIC("T", "话题附件"), KNOWLEDGE("K", "知识库附件");
	private String type;
	private String desc;

	private FileArticleTypeEnum(String type, String desc) {
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
	
	public static FileArticleTypeEnum getFileArticleTypeByType(String type){
		if(StringUtil.isEmpty(type)){
			return null;
		}
		for(FileArticleTypeEnum fileArticleType : FileArticleTypeEnum.values()){
			if(fileArticleType.getType().equals(type)){
				return fileArticleType;
			}
		}
		return null;
	}
}

package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.known.common.utils.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArticleType {
	SHUOSHUO("S","说说"), TOPIC("T","论坛"), BLOG("B", "话题"), KNOWLEDGE("K", "知识库"), Ask("A", "问答");
	
	private String type;
	
	private String desc;

	private ArticleType(String type, String desc) {
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

	public static ArticleType getArticleTypeByType(String type){
		if(StringUtils.isEmpty(type)){
			return null;
		}
		for(ArticleType articleType : ArticleType.values()){
			if(articleType.getType().equals(type)){
				return articleType;
			}
		}
		return null;
	}
	
}

package com.known.common.enums;

public enum BlogStatusEnum {
	DRAFT(0,"草稿"), PUBLIC(1, "发布");
	private Integer type;
	private String desc;
	
	private BlogStatusEnum(Integer type, String desc) {
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
	public static BlogStatusEnum getStatusByValue(Integer type){
		if(type == null){
			return null;
		}
		for(BlogStatusEnum bse : BlogStatusEnum.values()){
			if(bse.getType() == type){
				return bse;
			}
		}
		return null;
	}
}

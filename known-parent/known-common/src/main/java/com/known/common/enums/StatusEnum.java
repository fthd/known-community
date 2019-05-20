package com.known.common.enums;

public enum StatusEnum {
	INIT(0,"未审核"), AUDIT(1, "已审核");
	private Integer type;
	private String desc;
	
	private StatusEnum(Integer type, String desc) {
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
	public static StatusEnum getStatusByValue(Integer type){
		if(type == null){
			return null;
		}
		for(StatusEnum se : StatusEnum.values()){
			if(se.getType() == type){
				return se;
			}
		}
		return null;
	}
}

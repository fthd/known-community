package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExamChooseType {
	SingleSelection(1,"单选"), MultiSelection(2, "多选");
	private Integer type;
	private String desc;
	
	
	private ExamChooseType(Integer type, String desc) {
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
	public static ExamChooseType getExamChooseTypeByValue(Integer type){
		if(type == null){
			return null;
		}
		for(ExamChooseType ect : ExamChooseType.values()){
			if(ect.getType() == type){
				return ect;
			}
		}
		return null;
	}
	
}

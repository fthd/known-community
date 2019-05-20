package com.known.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SolveEnum {
	WAIT_SOLVE(0, "待解决"), SOLVED(1, "已解决");
	
	private Integer type;
	
	private String desc;

	private SolveEnum(Integer type, String desc) {
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
	
	public static SolveEnum getSolveEnumByType(Integer type){
		if(type == null){
			return null;
		}
		for(SolveEnum solveEnum : SolveEnum.values()){
			if(solveEnum.getType() == type){
				return solveEnum;
			}
		}
		return null;
	}
	
}

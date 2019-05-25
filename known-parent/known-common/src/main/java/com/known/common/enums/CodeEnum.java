package com.known.common.enums;



public enum CodeEnum {
	SUCCESS(200, "请求成功"),
	
	NOPERMISSION(403, "没有权限"),
	
	BUSSINESSERROR(415, "业务异常"),
	
	CODEERROR(425, "验证码错误"),
	
	SERVERERROR(500, "服务器错误"),
	
	LOGINTIMEOUT(401, "登录超时");
	private int code;
	private String desc;
	
	private CodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static CodeEnum getResponseByCode(Integer code){
		for(CodeEnum rc : CodeEnum.values()){
			if(rc.code == code){
				return rc;
			}
		}
		return null;
	}
}

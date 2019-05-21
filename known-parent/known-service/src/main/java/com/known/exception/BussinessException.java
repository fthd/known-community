package com.known.exception;

/**
 * 业务逻辑层异常处理
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 09:12
 */
public class BussinessException extends Exception{

	private static final long serialVersionUID = 8774764971037862955L;
	
	public BussinessException(String message, Throwable e) {
		super(message, e);
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}

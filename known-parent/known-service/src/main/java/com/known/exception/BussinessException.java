package com.known.exception;

public class BussinessException extends Exception{

	/**
	 * 
	 */
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

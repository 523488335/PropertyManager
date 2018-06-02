package com.csj.exception;
/**
 * 自定义异常
 * @author 陈尚均
 */
public class PropertyOSException extends Exception{
	
	private static final long serialVersionUID = -1523948590748511786L;
	
	private ErrCode errCode;
	
	public PropertyOSException(ErrCode errCode,String message){
		super(message);
		this.errCode = errCode;
	}
	public ErrCode getErrCode() {
		return errCode;
	}
	public void setErrCode(ErrCode errCode) {
		this.errCode = errCode;
	}
}

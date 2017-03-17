package com.logistics.common.utils.exception;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7310046427227110261L;

	public ServiceException() {
		super();
	}

	public ServiceException(String s) {
		super(s);
	}
	
	public ServiceException(Throwable e){
        super(e);
    }
	
	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }


}

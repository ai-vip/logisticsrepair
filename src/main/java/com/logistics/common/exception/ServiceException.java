package com.logistics.common.exception;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288895988701119701L;

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

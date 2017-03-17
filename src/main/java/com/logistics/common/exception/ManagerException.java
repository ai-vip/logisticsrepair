package com.logistics.common.exception;

public class ManagerException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1374520290618114323L;

	public ManagerException() {
		super();
	}

	public ManagerException(String s) {
		super(s);
	}
	
	public ManagerException(Throwable e){
        super(e);
    }
	
	public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }


}

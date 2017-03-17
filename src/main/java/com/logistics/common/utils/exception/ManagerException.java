package com.logistics.common.utils.exception;

public class ManagerException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8107528460053894519L;

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

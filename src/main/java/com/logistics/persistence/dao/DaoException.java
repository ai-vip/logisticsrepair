package com.logistics.persistence.dao;

public class DaoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7686538577887290612L;

	public DaoException() {
		super();
	}

	public DaoException(String s) {
		super(s);
	}
	
	public DaoException(Throwable e){
        super(e);
    }
	
	public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

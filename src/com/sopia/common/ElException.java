package com.sopia.common;


/**
 * ϵͳdao�쳣
 * @author Administrator
 *
 */
public class ElException extends Exception{
	
	private static final long serialVersionUID = -2868177264829616320L;
	public ElException() {
	}
	public ElException(String message){
		super(message);
	}
	public ElException(Throwable tb) {
		super(tb);
//		DBConnection.setNeedrollback();
	}
	public ElException(String message, Throwable tb) {
		super(message,  tb);
	}
}

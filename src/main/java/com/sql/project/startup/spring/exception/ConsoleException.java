package com.sql.project.startup.spring.exception;

/**
 * @ClassName:     ErrorException.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-07 12:51:27 
 */
public class ConsoleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String clazz;
	
	public ConsoleException(String errorCode,String clazz){
		this.errorCode=errorCode;
		this.clazz = clazz;
	}
	
	public String getMessage(){
		return this.clazz+" "+this.errorCode;
	}
	public String getErrorCode(){
		return this.errorCode;
	}

	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}

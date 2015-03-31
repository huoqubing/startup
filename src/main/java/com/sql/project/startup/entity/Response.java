package com.sql.project.startup.entity;

import java.util.List;

/**
 * @ClassName:     Response.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-12-25 下午8:18:27 
 */
public class Response<T> {

	private String status;
	private String message;
	private List<T> dataList=null;
	private T data=null;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the dataList
	 */
	public List<T> getDataList() {
		return dataList;
	}
	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
}

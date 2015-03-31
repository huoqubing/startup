package com.sql.project.startup.entity;

/**
 * @ClassName:     Page.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-12-11 下午10:54:25 
 */
public class Page {

	private int pageNumber;//页码
	private int pageSize;//每页数量
	private int start;//起始游标
	
	public Page(int pageNumber,int pageSize){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.start =(pageNumber-1)*pageSize;
	}

	public Page(int pageNumber){
		this.pageNumber = pageNumber;
		this.pageSize = 10;
		this.start =(pageNumber-1)*pageSize;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
}

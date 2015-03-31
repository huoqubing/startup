package com.sql.project.startup.entity;

import java.util.List;

/**
 * @ClassName:     ListResult.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-12-11 下午10:58:03 
 */
public class ListResult<T> {

	private int count;
	private Page page;
	private List<T> list;
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}
	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
}

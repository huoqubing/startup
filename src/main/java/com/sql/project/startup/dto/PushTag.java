package com.sql.project.startup.dto;

import com.sql.project.startup.spring.constraint.StringConstraint;

import java.util.Date;

/**
 * push 相关tag
 * @author SQL
 *  
 * 2014-2-28 下午10:21:15
 */
public class PushTag extends BaseDto{
	private static final long serialVersionUID = 1L;

	private int id;
	@StringConstraint(max=50)
	private String tag;
	@StringConstraint(max=50)
	private String title;
	private Date createtime;
	private Date updatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

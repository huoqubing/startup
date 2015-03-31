/**
 * 
 */
package com.sql.project.startup.dto;

import com.sql.project.startup.spring.constraint.DoubleConstraint;
import com.sql.project.startup.spring.constraint.StringConstraint;

import java.util.Date;

/**
 * TODO
 * @author SQL
 *  
 * 2014-1-1 下午12:17:37
 */
public class AppVersionDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String type;
	@DoubleConstraint(maxIntValue=2,scale=2)
	private String version;
	@StringConstraint(min=1,max=120)
	private String downloadUrl;
	@StringConstraint(min=1,max=120)
	private String content;
	private Date createTime;
	private Date updateTime;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}

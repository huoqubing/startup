package com.sql.project.startup.dto;

import java.util.Date;

/**
 * 短信发送记录
 * @author SQL
 *  
 * 2014-2-11 下午7:38:51
 */
public class SMSRecord extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//主键ID
	private String phone;//电话号码
	private String code;//验证码
	private Date createtime;//创建时间
	
	public SMSRecord(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}

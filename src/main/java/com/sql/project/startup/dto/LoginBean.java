package com.sql.project.startup.dto;

import com.sql.project.startup.spring.constraint.LoginName;
import com.sql.project.startup.spring.constraint.Password;


public class LoginBean extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386682283688735911L;
	@LoginName(min=6,max=20)
	private String loginName;
	@Password(min=6,max=20)
	private String password;
	private String validCode;
	
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	
	
	
}

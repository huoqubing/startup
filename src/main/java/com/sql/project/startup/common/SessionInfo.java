package com.sql.project.startup.common;

import com.sql.project.startup.entity.User;

import java.io.Serializable;

/**
 * 
 * @author zw
 * 
 */
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = 7859232412234858585L;

	private User loginUser;

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

}

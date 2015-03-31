/**
 * 
 */
package com.sql.project.startup.entity;

import java.io.Serializable;

/**
 * TODO
 * @author SQL
 *  
 * 2013-12-31 下午9:23:40
 */
public class UserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 角色ID
	 */
	private int roleId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}

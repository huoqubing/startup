package com.sql.project.startup.entity;

import java.io.Serializable;


public class RolePrivilege extends GenericEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private int roleId;
	/**
	 * 权限标识
	 */
	private int privilegeId;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	
}

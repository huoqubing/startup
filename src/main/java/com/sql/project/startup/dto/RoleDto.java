package com.sql.project.startup.dto;

import com.sql.project.startup.spring.constraint.StringConstraint;

public class RoleDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private int id;
	/**
	 * 角色标识
	 */
	@StringConstraint(min=1,max=32)
	private String roleId;
	/**
	 * 角色名称
	 */
	@StringConstraint(min=1,max=32)
	private String roleName;
	
	@StringConstraint(min=1,max=1000)
	private String privilegeIds;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
}

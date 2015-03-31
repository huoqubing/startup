package com.sql.project.startup.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Privilege extends GenericEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private int id;
	/**
	 * 权限标识
	 */
	private String privilegeId;
	/**
	 * 权限名称
	 */
	private String privilegeName;
	/**
	 * 描述
	 */
	private String desc;

	private List<Privilege> subPrivilege = new ArrayList<Privilege>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Privilege> getSubPrivilege() {
		return subPrivilege;
	}

	public void setSubPrivilege(List<Privilege> subPrivilege) {
		this.subPrivilege = subPrivilege;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((privilegeId == null) ? 0 : privilegeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Privilege other = (Privilege) obj;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}
}

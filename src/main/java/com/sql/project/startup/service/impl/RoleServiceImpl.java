package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.RoleDto;
import com.sql.project.startup.entity.*;
import com.sql.project.startup.service.RoleService;
import com.sql.project.startup.spring.exception.ConsoleException;
import com.sql.project.startup.spring.exception.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * TODO
 * @author SQL
 *  
 * 2014-1-5 下午12:35:29
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	public void add(Role entity) {
		String privilegeIdsStr = entity.getPrivilegeIds();
		if(StringUtils.isNotBlank(privilegeIdsStr)){
			entity.setCreateTime(new Date());
			myBatisDao.add("roleMapper.add", entity);
			String[] privilegeIds = privilegeIdsStr.split("\\|");
	          for (String privilegeId : privilegeIds) {
	        	  RolePrivilege rolePrivilege = new RolePrivilege();
	        	  rolePrivilege.setPrivilegeId(Integer.parseInt(privilegeId));
	        	  rolePrivilege.setRoleId(entity.getId());
	        	  rolePrivilege.setUpdateTime(new Date());
	        	  rolePrivilege.setCreateTime(new Date());
	             myBatisDao.add("roleMapper.addRolePrivilege", rolePrivilege);
	          }
		}
	}

	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("roleMapper.deleteRolePrivilegeById",id);
		if(myBatisDao.deleteById("roleMapper.deleteById", id)==0){
			throw new ConsoleException(ErrorCode.ERR_DELETE_FAIL,User.class.getName());
		}
	}

	@Override
	public void deleteByIds(List<Integer> ids) {
		myBatisDao.deleteByIds("roleMapper.deleteRolePrivilegeByIds",ids);
		myBatisDao.deleteByIds("roleMapper.deleteByIds", ids);
	}

	@Override
	public void update(Role entity) {
		String privilegeIdsStr = entity.getPrivilegeIds();
		if(StringUtils.isNotBlank(privilegeIdsStr)){
			myBatisDao.update("roleMapper.update", entity);
			myBatisDao.deleteById("roleMapper.deleteRolePrivilegeById", entity.getId());
			String[] privilegeIds = privilegeIdsStr.split("\\|");
	          for (String privilegeId : privilegeIds) {
	        	  RolePrivilege rolePrivilege = new RolePrivilege();
	        	  rolePrivilege.setPrivilegeId(Integer.parseInt(privilegeId));
	        	  rolePrivilege.setRoleId(entity.getId());
	        	  rolePrivilege.setUpdateTime(new Date());
	        	  rolePrivilege.setCreateTime(new Date());
	             myBatisDao.add("roleMapper.addRolePrivilege", rolePrivilege);
			}
		}
		
		
	}

	@Override
	public Role getById(int id) {
		Role role = myBatisDao.getById("roleMapper.getById", id);
		List<Privilege> list = myBatisDao.getList("roleMapper.getPrivilegeByRoleId", id);
	      StringBuilder sb = new StringBuilder();
	      if (list != null && !list.isEmpty()) {
	         for (Privilege privilege : list) {
	            if (sb.length() > 0) {
	               sb.append("|");
	            }
	            sb.append(privilege.getId());
	         }
	      }
	      role.setPrivilegeIds(sb.toString());
		return role;
	}
	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("roleMapper.getList");
	}

	@Override
	public PageBean search(RoleDto roleDto) {
		int page = 0;
		if (roleDto.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = roleDto.getCurrentPage();
		}

		int pageSize = roleDto.getPageSize();
		Role params = new Role();
		BeanUtils.copyProperties(roleDto, params);
		
		List<Role> result = myBatisDao.getList("roleMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("roleMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(roleDto.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}

	@Override
	public List<Privilege> getPrivilegeList() {
		return myBatisDao.getList("privilegeMapper.getList");
	}

	@Override
	public Role getByRoleId(String roleId) {
		return myBatisDao.get("roleMapper.getByRoleId",roleId);
	}

	@Override
	public boolean isUsed(String roleId) {
		boolean result = false;
		Role params = new Role();
		params.setId(Integer.valueOf(roleId));
		List<UserRole> list = myBatisDao.getList("roleMapper.getUserRoleByRoleId", params);
		if(null!=list && !list.isEmpty()){
			result = true;
		}
		return result;
	}
}

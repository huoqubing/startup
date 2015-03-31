package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.UserDto;
import com.sql.project.startup.entity.*;
import com.sql.project.startup.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName:     UserServiceImpl.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-07 10:02:46 
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MyBatisDao myBatisDao;

	@Override
	public void add(User entity) {
		String roleIdsStr = entity.getRoleIds();
		if(StringUtils.isNotBlank(roleIdsStr)){
			entity.setCreateTime(new Date());
			myBatisDao.add("userMapper.add", entity);
			String[] roleIds = roleIdsStr.split("\\|");
	          for (String roleId : roleIds) {
	             UserRole userRole = new UserRole();
	             userRole.setUserId(entity.getId());
	             userRole.setRoleId(Integer.parseInt(roleId));
	             myBatisDao.add("userMapper.addUserRole", userRole);
	          }
		}
	}

	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("userMapper.deleteUserRoleByUserId", id);
		myBatisDao.deleteById("userMapper.deleteById", id);
	}

	@Override
	public void deleteByIds(List<Integer> ids) {
		for(Integer id:ids){
			myBatisDao.deleteById("userMapper.deleteUserRoleByUserId", id);
		}
		myBatisDao.deleteByIds("userMapper.deleteByIds", ids);
	}

	@Override
	public void update(User entity) {
		String roleIdsStr = entity.getRoleIds();
		if(StringUtils.isNotBlank(roleIdsStr)){
			myBatisDao.update("userMapper.update", entity);
			myBatisDao.deleteById("userMapper.deleteUserRoleByUserId", entity.getId());
			String[] roleIds = roleIdsStr.split("\\|");
	          for (String roleId : roleIds) {
	             UserRole userRole = new UserRole();
	             userRole.setUserId(entity.getId());
	             userRole.setRoleId(Integer.parseInt(roleId));
	             myBatisDao.add("userMapper.addUserRole", userRole);
			}
		}
	}
	
	

	@Override
	public User getById(int id) {
		User user = myBatisDao.getById("userMapper.getById", id);
		if(null!=user){
			List<UserRole> list = myBatisDao.getList("userMapper.getUserRoleById", id);
		      StringBuilder sb = new StringBuilder();
		      if (list != null && !list.isEmpty()) {
		         for (UserRole userRole : list) {
		            if (sb.length() > 0) {
		               sb.append("|");
		            }
		            sb.append(userRole.getRoleId());
		         }
		      }
		      user.setRoleIds(sb.toString());
		}
		return user;
	}

	@Override
	public User login(String loginName, String password) {
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		
		User result = myBatisDao.get("userMapper.login", user);
		if(null!=result){
			List<Role> roleList = myBatisDao.getList("roleMapper.getByLoginUser", result);
			Set<Privilege> privilegeSet = new HashSet<Privilege>();
			Set<String> privilegeIdSet = new HashSet<String>();
			for(Role role:roleList){
				List<Privilege> privileges = myBatisDao.getList("roleMapper.getPrivilegeByRoleId", role.getId());
				privilegeSet.addAll(privileges);
				for(Privilege p:privileges){
					privilegeIdSet.add(p.getPrivilegeId());
				}
			}
			List<Menu> allSubMenu = myBatisDao.getList("menuMapper.getList");
			Map<Integer,List<Menu>> parentMenus = new HashMap<Integer,List<Menu>>();
			for(Menu m:allSubMenu){
				if(privilegeIdSet.contains(m.getPrivilegeId())){
					if(m.getParent()>0){
						List<Menu> subMenu = parentMenus.get(m.getParent());
						if(null==subMenu){
							subMenu = new ArrayList<Menu>();
							parentMenus.put(m.getParent(), subMenu);
						}
						subMenu.add(m);
					}
				}
			}
			List<Menu> menuList = new ArrayList<Menu>();
			for(Integer parentMenuId:parentMenus.keySet()){
				Menu parentMenu = myBatisDao.getById("menuMapper.getById", parentMenuId);
				if(null!=parentMenu){
					parentMenu.getSubMenus().addAll(parentMenus.get(parentMenuId));
					menuList.add(parentMenu);
				}
			}
			result.getMenuList().addAll(menuList);
			result.getPrivilegeList().addAll(privilegeSet);
		}
		return result;
	}
	
	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("userMapper.getList");
	}

	@Override
	public User getByLoginName(String loginName) {
		return myBatisDao.get("userMapper.getByLoginName",loginName);
	}

	@Override
	public PageBean search(UserDto userDto) {
		int page = 0;
		if (userDto.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = userDto.getCurrentPage();
		}

		int pageSize = userDto.getPageSize();
		User params = new User();
		BeanUtils.copyProperties(userDto, params);
		
		List<User> result = myBatisDao.getList("userMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("userMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(userDto.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}

	@Override
	public List<Role> getRoleList() {
		return myBatisDao.getList("roleMapper.getList");
	}

	@Override
	public void updatePsw(User entity) {
		myBatisDao.update("userMapper.updatePsw", entity);
	}
}

package com.sql.project.startup.service;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.UserDto;
import com.sql.project.startup.entity.Role;
import com.sql.project.startup.entity.User;

import java.util.List;


/**
 * @ClassName:     UserService.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-07 09:55:51 
 */
public interface UserService extends BaseService<User> {

	User login(String loginName, String password);
	
	User getByLoginName(String loginName);
	
	public PageBean search(UserDto userDto);
	
    public List<Role> getRoleList();
    
    public void updatePsw(User user);
}

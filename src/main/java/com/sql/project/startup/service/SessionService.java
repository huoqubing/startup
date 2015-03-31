package com.sql.project.startup.service;

import com.sql.project.startup.entity.Session;

/**
 * @ClassName:     SessionService.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-13 21:57:47 
 */
public interface SessionService{

	public void add(Session session);
	
	public void deleteByUserID(int userId);
	
	public void deleteByCookieKey(String cookieKey);
	
	public void delete(Session session);
	
	public void updateSession(Session session);
	
	public Session getByUserId(int userID);
	
	public Session getByCookieKey(String cookieKey);
	
	public void refreshTimeOut(int userId);
}

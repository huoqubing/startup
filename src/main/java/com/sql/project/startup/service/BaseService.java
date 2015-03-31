package com.sql.project.startup.service;

import java.util.List;

/**
 * @ClassName: BaseService.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-07-07 09:22:24
 */
public interface BaseService<T> {

	void add(T entity);

	void deleteById(int id);
	
	void deleteByIds(List<Integer> ids);

	void update(T entity);

	T getById(int id);
	
	<T> List<T> getList() ;
}

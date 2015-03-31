package com.sql.project.startup.dao;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:     MyBatisDao.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-06 13:03:33 
 */
public class MyBatisDao extends SqlSessionDaoSupport {

	/** 
	 * @Title:        add 
	 * @Description:  新增数据
	 * @param:        key:Mapper
	 * @param:        object:操作对象
	 * @return:       int  0:新增失败 1:新增成功  
	 * @throws 
	 */
	public int add(String key, Object object) {
		return getSqlSession().insert(key, object);
	}
	
	/** 
	 * @Title:        deleteById 
	 * @Description:  通过主键删除数据
	 * @param:        key:mapper
	 * @param:        id:主键
	 * @return:       int 0:删除失败  1:删除成功   
	 * @throws 
	 */
	public int deleteById(String key, Serializable id) {
		return getSqlSession().delete(key, id);
	}

	public void deleteByIds(String key, List<Integer> ids) {
		getSqlSession().delete(key, ids);
	}
	
	/** 
	 * @Title:        update 
	 * @Description:  更新操作
	 * @param:        key:mapper
	 * @param:        object:要删除的对象
	 * @return:       int 0:删除失败  1:删除成功      
	 * @throws 
	 */
	public int update(String key,Object object){
		return getSqlSession().update(key, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object params) {
		return (T) getSqlSession().selectOne(key, params);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getById(String key,int id){
		return (T) getSqlSession().selectOne(key, id);
	}
	
	public <T> List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}
	
	public <T> List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}
	
	public <T> List<T> getList(String key, Object params,int offset,int limit) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		return getSqlSession().selectList(key, params,rowBounds);
	}
	
	public Long getCount(String key, Object params){
		return getSqlSession().selectOne(key, params);
	}
}

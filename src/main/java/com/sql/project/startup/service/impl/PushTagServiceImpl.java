/**
 * 
 */
package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushTag;
import com.sql.project.startup.service.PushTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 * @author SQL
 *  
 * 2014-1-1 下午1:49:02
 */
@Service
public class PushTagServiceImpl implements PushTagService {
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Override
	public PageBean search(PushTag pushTag) {

		int page = 0;
		if (pushTag.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = pushTag.getCurrentPage();
		}

		int pageSize = pushTag.getPageSize();
		PushTag params = new PushTag();
		BeanUtils.copyProperties(pushTag, params);
		
		List<PushTag> result = myBatisDao.getList("pushTagMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("pushTagMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(pushTag.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}

	@Override
	public void update(PushTag item) {
		myBatisDao.update("pushTagMapper.update", item);
	}


	@Override
	public void add(PushTag item) {
		myBatisDao.add("pushTagMapper.add", item);
	}


	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("pushTagMapper.deleteById", id);
	}


	@Override
	public void deleteByIds(List<Integer> ids) {
		myBatisDao.deleteByIds("pushTagMapper.deleteByIds", ids);
	}
	
	@Override
	public PushTag getById(int id) {
		return myBatisDao.getById("pushTagMapper.getById", id);
	}


	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("pushTagMapper.getList", new PushTag());
	}

}

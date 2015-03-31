/**
 * 
 */
package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.AppVersionDto;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.entity.AppVersion;
import com.sql.project.startup.service.AppVersionService;
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
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Override
	public PageBean search(AppVersionDto appVersionDto) {

		int page = 0;
		if (appVersionDto.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = appVersionDto.getCurrentPage();
		}

		int pageSize = appVersionDto.getPageSize();
		AppVersion params = new AppVersion();
		BeanUtils.copyProperties(appVersionDto, params);
		
		List<AppVersion> result = myBatisDao.getList("appVersionMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("appVersionMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(appVersionDto.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}


	@Override
	public Boolean checkIsExists(Integer id) {
		return myBatisDao.getById("appVersionMapper.getById", id)==null?false:true;
	}

	@Override
	public void update(AppVersion item) {
		myBatisDao.update("appVersionMapper.update", item);
	}


	@Override
	public void add(AppVersion item) {
		myBatisDao.add("appVersionMapper.add", item);
	}


	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("appVersionMapper.deleteById", id);
	}


	@Override
	public void deleteByIds(List<Integer> ids) {
		myBatisDao.deleteByIds("appVersionMapper.deleteByIds", ids);
	}
	
	@Override
	public AppVersion getById(int id) {
		return myBatisDao.getById("appVersionMapper.getById", id);
	}


	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("appVersionMapper.getList", new AppVersion());
	}

}

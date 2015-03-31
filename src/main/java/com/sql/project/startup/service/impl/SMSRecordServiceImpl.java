/**
 * 
 */
package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.SMSRecord;
import com.sql.project.startup.service.SMSRecordService;
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
public class SMSRecordServiceImpl implements SMSRecordService {
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Override
	public PageBean search(SMSRecord smsRecord) {

		int page = 0;
		if (smsRecord.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = smsRecord.getCurrentPage();
		}

		int pageSize = smsRecord.getPageSize();
		SMSRecord params = new SMSRecord();
		BeanUtils.copyProperties(smsRecord, params);
		
		List<SMSRecord> result = myBatisDao.getList("smsRecordMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("smsRecordMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(smsRecord.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}

	@Override
	public void update(SMSRecord item) {
		myBatisDao.update("smsRecordMapper.update", item);
	}


	@Override
	public void add(SMSRecord item) {
		myBatisDao.add("smsRecordMapper.add", item);
	}


	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("smsRecordMapper.deleteById", id);
	}


	@Override
	public void deleteByIds(List<Integer> ids) {
		myBatisDao.deleteByIds("smsRecordMapper.deleteByIds", ids);
	}
	
	@Override
	public SMSRecord getById(int id) {
		return myBatisDao.getById("smsRecordMapper.getById", id);
	}


	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("smsRecordMapper.getList", new SMSRecord());
	}

}

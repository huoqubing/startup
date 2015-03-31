package com.sql.project.startup.service;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushRecord;

public interface PushRecordService extends BaseService<PushRecord> {
	
	public PageBean search(PushRecord pushRecord);
}

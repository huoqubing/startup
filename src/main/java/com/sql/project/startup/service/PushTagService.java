package com.sql.project.startup.service;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushTag;

public interface PushTagService extends BaseService<PushTag> {
	
	public PageBean search(PushTag pushTag);
}

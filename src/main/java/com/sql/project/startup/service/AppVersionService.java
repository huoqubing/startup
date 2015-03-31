package com.sql.project.startup.service;

import com.sql.project.startup.dto.AppVersionDto;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.entity.AppVersion;

public interface AppVersionService extends BaseService<AppVersion> {
	
	public PageBean search(AppVersionDto appVersion);

	public Boolean checkIsExists(Integer id);
}

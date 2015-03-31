package com.sql.project.startup.service;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.SMSRecord;


/**
 * @ClassName:     UserService.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-07 09:55:51 
 */
public interface SMSRecordService extends BaseService<SMSRecord> {
	public PageBean search(SMSRecord smsRecord);
}

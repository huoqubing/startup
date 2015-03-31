/**
 * 
 */
package com.sql.project.startup.service.impl;

import com.sql.project.startup.dao.MyBatisDao;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushRecord;
import com.sql.project.startup.service.PushRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * TODO
 * @author SQL
 *  
 * 2014-1-1 下午1:49:02
 */
@Service
public class PushRecordServiceImpl implements PushRecordService {
	private static final Logger logger = Logger.getLogger(PushRecordServiceImpl.class);
	@Autowired
	private MyBatisDao myBatisDao;
	
	private String masterSecret="560b0f89147114319dabc5f1";
	private String appKey="81505c2b859cfaad07b5a10d";
	
	@Override
	public PageBean search(PushRecord pushRecord) {

		int page = 0;
		if (pushRecord.getCurrentPage() <= 0) {
			page = 1;
		} else {
			page = pushRecord.getCurrentPage();
		}

		int pageSize = pushRecord.getPageSize();
		PushRecord params = new PushRecord();
		BeanUtils.copyProperties(pushRecord, params);
		
		List<PushRecord> result = myBatisDao.getList("pushRecordMapper.getList", params, (page - 1) * pageSize, pageSize);
		Long rows = myBatisDao.getCount("pushRecordMapper.getCount", params);
		
		PageBean bean = new PageBean();
		bean.setCurrentPage(page);
		bean.setPageSize(pushRecord.getPageSize());
		bean.setTotalRows(rows.intValue());
		bean.setResult(result);
		return bean;
	}

	@Override
	public void update(PushRecord item) {
		myBatisDao.update("pushRecordMapper.update", item);
	}


	@Override
	public void add(PushRecord item) {
		logger.info("push: sendno:"+item.getSendno()+" title:"+item.getTitle()+" content:"+item.getContent()+" receiverType:"+item.getReceivertype()+" receiverValue:"+item.getReceivervalue());
		/*try{
			JPushClient jpush = new JPushClient(masterSecret, appKey);
			NotificationParams params = new NotificationParams();
			if(item.getReceivertype().equals(ReceiverTypeEnum.ALIAS.value()+"")){
				params.setReceiverType(ReceiverTypeEnum.ALIAS);
			}else if(item.getReceivertype().equals(ReceiverTypeEnum.TAG.value()+"")){
				params.setReceiverType(ReceiverTypeEnum.TAG);
			}else{
				params.setReceiverType(ReceiverTypeEnum.APP_KEY);
			}
			params.setReceiverValue(item.getReceivervalue());
			MessageResult msgResult = jpush.sendNotification(item.getContent(), params, null);
			if (msgResult.isResultOK()) {
				 logger.info("msgResult - " + msgResult);
				 logger.info("messageId - " + msgResult.getMessageId());
			} else {
			    if (msgResult.getErrorCode() > 0) {
			        // 业务异常
			        logger.warn("Service error - ErrorCode: "
			                + msgResult.getErrorCode() + ", ErrorMessage: "
			                + msgResult.getErrorMessage());
			    } else {
			        // 未到达 JPush 
			        logger.error("Other excepitons - "
			                + msgResult.responseResult.exceptionString);
			    }
			}
			item.setStatuscode(msgResult.getErrorCode()+"");
			item.setStatus(msgResult.getErrorMessage());
		}catch(Exception e){
			logger.warn("exception,",e);
			item.setStatuscode("exception");
			item.setStatus("发送失败");
		}*/
		item.setCreatetime(new Date());
		item.setSendno(1);
		item.setType("1");
		myBatisDao.add("pushRecordMapper.add", item);
	}


	@Override
	public void deleteById(int id) {
		myBatisDao.deleteById("pushRecordMapper.deleteById", id);
	}


	@Override
	public void deleteByIds(List<Integer> ids) {
		myBatisDao.deleteByIds("pushRecordMapper.deleteByIds", ids);
	}
	
	@Override
	public PushRecord getById(int id) {
		return myBatisDao.getById("pushRecordMapper.getById", id);
	}


	@Override
	public <T> List<T> getList() {
		return myBatisDao.getList("pushRecordMapper.getList", new PushRecord());
	}

}

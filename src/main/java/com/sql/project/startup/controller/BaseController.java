package com.sql.project.startup.controller;

import com.sql.project.startup.common.Constant;
import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.common.SessionInfo;
import com.sql.project.startup.entity.User;
import com.sql.project.startup.spring.exception.ConsoleException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:     BaseController.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-06 13:35:51 
 */
public class BaseController  implements MessageConstant, Constant  {
	private static final Logger logger = Logger.getLogger(BaseController.class);
	@ExceptionHandler(value={Exception.class})  
	public String exp(Exception ex,HttpServletRequest request) { 
		if (ex instanceof ConsoleException) { 
			logger.error(((ConsoleException) ex).getMessage(),ex);
		}else{
			logger.error("BaseController Error",ex);
		}
		
		return "error/403";  
	}
	

	protected SessionInfo getSessionInfo(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute(KEY_SESSION_INFO);
		if (sessionInfo == null) {
			sessionInfo = new SessionInfo();
			session.setAttribute(KEY_SESSION_INFO, sessionInfo);
		}
		return sessionInfo;

	}

	protected void setMessage(String type, String messageCode,
			HttpServletRequest request) {
		Map<String, String> messageMap = (Map<String, String>) request
				.getAttribute(KEY_REQUEST_MESSAGE);
		if (messageMap == null) {
			messageMap = new HashMap<String, String>();

		}

		messageMap.put(type, messageCode);
		request.setAttribute(KEY_REQUEST_MESSAGE, messageMap);
	}

	protected Map<String, String> getMessage(String type, String messageCode,
			HttpServletRequest request) {
		Map<String, String> messageMap = (Map<String, String>) request
				.getAttribute(KEY_REQUEST_MESSAGE);
		if (messageMap == null) {
			messageMap = new HashMap<String, String>();

		}
		messageMap.put(type, messageCode);
		return messageMap;
	}

	protected void setCurrentTopMenu(String menuKey, HttpServletRequest request) {
		request.setAttribute(KEY_REQUEST_CURRENT_TOP_MENU, menuKey);
	}

	protected void setCurrentLeftMenu(String menuKey, HttpServletRequest request) {
		request.setAttribute(KEY_REQUEST_CURRENT_LEFT_MENU, menuKey);
	}

	protected String getCurretnUserName(HttpServletRequest request) {
		User user = this.getSessionInfo(request.getSession()).getLoginUser();
		if (user == null) {
			return "noboday";
		}
		return user.getLoginName();
	}
	
	protected User getCurretnUser(HttpServletRequest request) {
		 return this.getSessionInfo(request.getSession()).getLoginUser();
	}

	protected void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration names = session.getAttributeNames();

		while (names.hasMoreElements()) {
			String attrName = (String) names.nextElement();
			session.removeAttribute(attrName);
		}

	}

	/***************************************************************************************************************************
	 * Common JSON object *
	 * *************************************************************************************************************************/
	public class CommonJSON implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6638198187315651901L;
		private String status;
		private String message;
		private Object data;

		public CommonJSON() {
		}

		public CommonJSON(String status, String message) {
			super();
			this.status = status;
			this.message = message;
		}

		public CommonJSON(String status, String message, Object data) {
			super();
			this.status = status;
			this.message = message;
			this.data = data;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		@Override
		public String toString() {
			JSONObject job = JSONObject.fromObject(this);
			String result = job.toString();
			return result;
		}

	}	
}

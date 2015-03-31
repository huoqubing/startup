package com.sql.project.startup.common;

/**
 * @ClassName:     MessageConstant.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-06 13:33:54 
 */
public interface MessageConstant {

	String MESSAGE_TYPE_SUCCESS="success";
	String MESSAGE_TYPE_ERROR="error";
	String MESSAGE_TYPE_EXCEPTION="exception";
	
	String SUCCESS_OPERATION = "success.operation";
	
	String ERROR_CAPTCHA_REQUIRED ="{err.captcha.required}";
	//LoginBean
	String ERROR_LOGIN_NAME_REQUIRED="{err.login.name.required}";
	String ERROR_LOGIN_PASSWORD_REQUIRED = "{err.login.password.required}";
	String ERROR_LOGIN_NAME_WRONG="{err.login.name.wrong}";
	String ERROR_LOGIN_PASSWORD_WRONG = "{err.login.password.wrong}";
	String ERROR_LOGIN_NAME_LENGTH="{err.login.name.length}";
	String ERROR_LOGIN_PASSWORD_LENGTH = "{err.login.password.length}";
	String ERROR_LOGIN_NAME_PATTERN="{err.login.name.pattern}";
	String ERROR_LOGIN_NAME_PASSWORD_WRONG="err.login.name.password.wrong";
	String ERROR_CAPTCHA="err.captcha";
	
	//appVersion
	String ERROR_APP_VERSION_TYPE_REQUIRED="{err.appVersion.type.required}";
	String ERROR_APP_VERSION_VERSION_REQUIRED="{err.appVersion.version.required}";
	String ERROR_APP_VERSION_TYPE_VERSION_EXIST="{err.appVersion.type.version.exist}";
	
	//common
	String ERROR_REQUIRED="{err.required}";
	String ERROR_LENGTH = "{err.length}";
	
	//email
	String ERROR_EMAIL_REQUIRED="{err.email.required}";
	String ERROR_EMAIL_LENGTH = "{err.email.length}";
	String ERROR_EMAIL_PATTERN="{err.email.pattern}";
	
	//phone
	String ERROR_PHONE="{err.phone}";
	
	//integer
	String ERROR_INTEGER="{err.integer}";
	
	//double
	String ERROR_DOUBLE="{err.double}";
	
	//date
	String ERROR_DATE="{err.date}";
	
	String ERROR_ORIGINAL_PSW="err.original.password";
	String ERROR_CONFIRM_PSW="err.confirm.password";
	String ERROR_USER_NOT_EXIST="err.user.not.exist";
	
	String ERROR_ROLE_IS_USED="err.role.used";
}

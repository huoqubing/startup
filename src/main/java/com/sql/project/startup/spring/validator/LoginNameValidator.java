package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.LoginName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @ClassName: LoginNameValidator.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-7-28 下午2:34:05
 */
public class LoginNameValidator implements
		ConstraintValidator<LoginName, String>, BaseValidator {

	private String loginNameReg = "^[a-zA-Z_]\\w{min,max}$";
	private Pattern loginNamePattern;
	private LoginName loginName;
	@Override
	public void initialize(LoginName constraintAnnotation) {
		loginName = constraintAnnotation;
		loginNameReg = loginNameReg.replace("min", String.valueOf(loginName.min())).replace("max", String.valueOf(loginName.max()));
		loginNamePattern = Pattern.compile(loginNameReg);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.equals("")) {
			return false;
		}
		int length = value.length();
		if (length > loginName.max() || length < loginName.min()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_LOGIN_NAME_LENGTH).addConstraintViolation();
			return false;
		}
		if (!loginNamePattern.matcher(value).matches()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_LENGTH).addConstraintViolation();
		}
		return true;
	}
}

package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName:     PasswordValidator.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-7-28 下午3:37:04 
 */
public class PasswordValidator implements ConstraintValidator<Password, String>,
		BaseValidator {

	private Password password;
	@Override
	public void initialize(Password constraintAnnotation) {
		password = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.equals("")) {
			return false;
		}
		int length = value.length();
		if (length > password.max() || length < password.min()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_LOGIN_PASSWORD_LENGTH).addConstraintViolation();
			return false;
		}
		return true;
	}

}

package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.Email;
import org.apache.commons.lang.StringUtils;

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
public class EmailValidator implements
		ConstraintValidator<Email, String>, BaseValidator {

	private String emailReg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	private Pattern emailPattern;
	private Email email;
	@Override
	public void initialize(Email constraintAnnotation) {
		email = constraintAnnotation;
		emailReg = emailReg.replace("min", String.valueOf(email.min())).replace("max", String.valueOf(email.max()));
		emailPattern = Pattern.compile(emailReg);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!email.isMandatory()&&
				StringUtils.isBlank(value)){
			return true;
		}
		
		if (email.isMandatory()&&
				StringUtils.isBlank(value)) {
			return false;
		}
		int length = value.length();
		if (length > email.max() || length < email.min()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_EMAIL_LENGTH).addConstraintViolation();
			return false;
		}
		if (!emailPattern.matcher(value).matches()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_EMAIL_PATTERN).addConstraintViolation();
			return false;
		}
		return true;
	}
}

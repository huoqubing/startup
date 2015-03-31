package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.StringConstraint;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * String 通用校验器
 * @author SQL
 *  
 * 2014-2-18 下午8:25:13
 */
public class StringValidator implements
		ConstraintValidator<StringConstraint, String>, BaseValidator {

	private StringConstraint stringValue;
	@Override
	public void initialize(StringConstraint constraintAnnotation) {
		stringValue = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!stringValue.isMandatory()&&
				StringUtils.isBlank(value)){
			return true;
		}
		
		if (stringValue.isMandatory()&&
				StringUtils.isBlank(value)) {
			return false;
		}
		int length = value.length();
		if (length > stringValue.max() || length < stringValue.min()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					ERROR_LENGTH).addConstraintViolation();
			return false;
		}
		return true;
	}
}

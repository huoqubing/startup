package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.DateConstraint;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @ClassName: LoginNameValidator.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-7-28 下午2:34:05
 */
public class DateValidator implements
		ConstraintValidator<DateConstraint, String>, BaseValidator {

	private DateConstraint dateConstraint;
	@Override
	public void initialize(DateConstraint constraintAnnotation) {
		dateConstraint = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)&&
				!dateConstraint.isMandatory()){
			return true;
		}
		if(StringUtils.isBlank(value)&&
				dateConstraint.isMandatory()){
			return false;
		}
		
		try{
			DateFormat df = new SimpleDateFormat(dateConstraint.pattern());
			df.parse(value);
			return true;
		}catch(Exception e){
			//ignore
		}
		return false;
	}
}

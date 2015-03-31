package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.IntegerConstraint;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName:     PasswordValidator.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-7-28 下午3:37:04 
 */
public class IntegerValidator implements ConstraintValidator<IntegerConstraint, String>,
		BaseValidator {

	private IntegerConstraint integerValue;
	@Override
	public void initialize(IntegerConstraint constraintAnnotation) {
		integerValue = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(!integerValue.isMandatory()&&
				StringUtils.isBlank(value)){
			return true;
		}
		
		if (integerValue.isMandatory()&&
				StringUtils.isBlank(value)) {
			return false;
		}
		try{
			Integer intValue = Integer.parseInt(value);
			if(intValue>=integerValue.min()&&intValue<=integerValue.max()){
				return true;
			}
		}catch(Exception e){
			//ignore
		}
		return false;
	}

}

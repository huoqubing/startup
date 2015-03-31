package com.sql.project.startup.spring.constraint;

import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.spring.validator.PhoneConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

	
	String message() default MessageConstant.ERROR_PHONE;
	
	boolean isMandatory() default true;
	
	boolean onlyMobile() default true;
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	 
}
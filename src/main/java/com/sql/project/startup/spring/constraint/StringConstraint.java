package com.sql.project.startup.spring.constraint;

import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.spring.validator.StringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * string 通用校验
 * @author SQL
 *  
 * 2014-2-18 下午8:24:39
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringValidator.class)
public @interface StringConstraint {

	String message() default MessageConstant.ERROR_REQUIRED;

	int min() default 1;

	int max();

	boolean isMandatory() default true;
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

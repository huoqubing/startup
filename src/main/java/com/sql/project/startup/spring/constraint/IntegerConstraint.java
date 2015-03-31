package com.sql.project.startup.spring.constraint;


import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.spring.validator.IntegerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:     Password.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-7-28 下午3:37:32 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerValidator.class)
public @interface IntegerConstraint {

	String message() default MessageConstant.ERROR_INTEGER;

	int min() default 1;

	int max();
	
	boolean isMandatory() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

package com.sql.project.startup.spring.constraint;


import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.spring.validator.DoubleValidator;

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
@Constraint(validatedBy = DoubleValidator.class)
public @interface DoubleConstraint {

	String message() default MessageConstant.ERROR_DOUBLE;
	
	int scale() default 2;
	int maxIntValue();
	boolean isMandatory() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

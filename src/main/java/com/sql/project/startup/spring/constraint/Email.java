package com.sql.project.startup.spring.constraint;

import com.sql.project.startup.common.MessageConstant;
import com.sql.project.startup.spring.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: LoginName.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-7-28 下午2:13:19
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

	String message() default MessageConstant.ERROR_EMAIL_REQUIRED;

	int min();

	int max();
	
	boolean isMandatory() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

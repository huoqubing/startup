package com.sql.project.startup.spring.annotation;

import java.lang.annotation.*;

/**
 * @ClassName:     FuncNum.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-13 14:44:16 
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface FuncNum {

	public String value();
	public String remark();
}

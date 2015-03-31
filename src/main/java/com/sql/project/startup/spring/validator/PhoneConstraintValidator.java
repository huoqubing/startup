package com.sql.project.startup.spring.validator;

import com.sql.project.startup.spring.constraint.Phone;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

	private String mobilePhone="^1(3[0-9]|45|47|5[0-35-9]|8[0-9])\\d{8}$";
	private String telephone="\\d{2,5}-\\d{7,8}(-\\d{1,})?";
	
    	
	private Phone phone;
	@Override
	public void initialize(Phone phone) {
		this.phone = phone;
	}

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
		if(!phone.isMandatory()&&
				StringUtils.isBlank(phoneField)){
			return true;
		}
		
		if (phone.isMandatory()&&
				StringUtils.isBlank(phoneField)) {
			return false;
		}
		if(phone.onlyMobile()){
			return phoneField.matches(mobilePhone);
		}else{
			return phoneField.matches(mobilePhone)||
					phoneField.matches(telephone);
		}
		
		
	}

}

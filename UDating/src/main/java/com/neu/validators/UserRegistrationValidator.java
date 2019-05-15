package com.neu.validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.edu.dao.LoginDao;
import com.neu.edu.model.Login;
import com.neu.edu.model.User;

public class UserRegistrationValidator implements Validator {
	private LoginDao loginDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "error.fullName.required", "fullName mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "error.accountId.required", "accountId mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "error.emailId.required", "emailId mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.required", "password mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "error.age.required", "age mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agePreference", "error.agePreference.required", "agePreference mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "error.sex.required", "sex mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexPreference", "error.sexPreference.required", "sexPreference mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location.street", "error.location.street.required", "street mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location.city", "error.location.city.required", "city mandatary");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location.country", "error.location.country.required", "country mandatary");
		
		System.out.println(errors.getErrorCount());
		if(errors.hasErrors()) return;
		
		User user = (User) target;
		
		if(!user.getFullName().matches("([A-Z][a-z]*\\s)+[A-Z][a-z]*")) errors.rejectValue("fullName", "error.fullName.format", "Name Format wrong");
		if(!user.getAccountId().matches("\\w{7,}")) errors.rejectValue("accountId", "error.accountId.format", "AccountId format wrong");
		if(!user.getEmailId().matches("\\w+@gmail.com")) errors.rejectValue("emailId", "error.emailId.format", "not gmail emailId format");
		if(!user.getPassword().matches("\\w{7,}")) errors.rejectValue("password", "error.password.format", "password format wrong");
		if(user.getAge()<18 || user.getAge()>50) errors.rejectValue("age", "error.age.format", "no minors allowed");
		if(user.getAgePreference()<18 || user.getAgePreference()>50) errors.rejectValue("agePreference", "error.agePreference.format", "can't like minors");
		if(user.getSex()!='M' && user.getSex()!='F') errors.rejectValue("sex", "error.sex.format", "has to be either male or female");
		if(user.getSexPreference()!='M' && user.getSexPreference()!='F') errors.rejectValue("sexPreference", "error.sexPreference.format", "Preference has to be either male or female");
		
		System.out.println(errors.getErrorCount());
		if(errors.hasErrors()) return;
		
		System.out.println(loginDao);
		if(loginDao.AccountIdExists(user.getAccountId())) errors.rejectValue("accountId", "error.accountId.format", "Account Id already taken");
		
		return;
	}
	
	public LoginDao getLoginDao() {
		return loginDao;
	}
	
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
}

package com.neu.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.edu.dao.LoginDao;
import com.neu.edu.model.Login;

public class LoginValidator implements Validator {
	private LoginDao loginDao;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Login.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "accountId.required", "AccountId required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password required");
		
		System.out.println("--------In Login Interceptor with error count: "+errors.getErrorCount());
		
		if(errors.hasErrors()) return;
		
		Login login = (Login) target;
		Login temp = loginDao.get(login.getAccountId(), login.getPassword());
		if(temp == null ) {
			errors.rejectValue("accountId", "error.accountId.format", "AccountId might be wrong");
			errors.rejectValue("password", "error.password.format", "Password might be wrong");
		}else {
			login.setRole(temp.getRole());
		}
		
		System.out.println("--------In Login Interceptor with error count: "+errors.getErrorCount());
		return;
	}
	
	public void validateLoginIdFields(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "accountId.required", "accountId required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "emailId.required", "emailId required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "password required");
		
		if(errors.hasErrors()) return;
		
		Login login = (Login) target;
		Login temp = loginDao.checkIds(login.getAccountId(), login.getEmailId());
		if(temp == null ) {
			errors.rejectValue("accountId", "error.accountId.format", "AccountId might be wrong");
			errors.rejectValue("emailId", "error.emailId.format", "EmailId might be wrong");
		}else {
			login.setRole(temp.getRole());
		}
	}
	
	public LoginDao getLoginDao() {
		return loginDao;
	}
	
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
}

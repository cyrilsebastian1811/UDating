package com.neu.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.neu.edu.dao.AdminDao;
import com.neu.edu.dao.LocationDao;
import com.neu.edu.dao.LoginDao;
import com.neu.edu.dao.MatchedDao;
import com.neu.edu.dao.UserDao;
import com.neu.edu.model.Admin;
import com.neu.edu.model.Login;
import com.neu.edu.model.Matched;
import com.neu.edu.model.User;
import com.neu.validators.LoginValidator;
import com.neu.validators.UserRegistrationValidator;


@Controller
public class HomeController {
	@Autowired
	BCryptPasswordEncoder security;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	MatchedDao matchedDao;
	
	@Autowired
	AdminDao adminDao;
	
	@Autowired
	private LoginValidator loginValidation;
	
	
	@RequestMapping(value = "/")
	public ModelAndView home(@ModelAttribute("login") @Valid Login login, BindingResult errors, @RequestParam(value = "submit", required = false) String submit, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Login account = null;
		String redirectURL = "";
		System.out.println("In Home Controller '/'");
		loginValidation.validate(login, errors);
		if(!errors.hasErrors()) {
			try {
				if(submit.equals("login")) {
					if(login.getRole() == 'U') {
						account = userDao.get(login.getAccountId(), login.getPassword());
						redirectURL = "userAccount";
					}else if(login.getRole() == 'A') {
//							account = marketerDao.get(accountId, password);
						redirectURL = "marketerAccount";
					}
					session.setAttribute("account", account);
					return new ModelAndView(new RedirectView(redirectURL));
				}
			}catch(Exception exc) {
				exc.printStackTrace();
				System.out.println("xxxxxxxxx Error at '/' in Home Controller");
			}
		}
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/changePassword")
	public ModelAndView ChangePassword(@ModelAttribute("login") @Valid Login login, BindingResult errors,
			@RequestParam(value = "submit", required = false) String submit, HttpServletRequest request) {
		
		loginValidation.validateLoginIdFields(login, errors);
		if(!errors.hasErrors()) {
			try {
				if(submit.equals("confirm")) {
					login.setPassword(security.encode(login.getPassword()));
					loginDao.updatePassword(login.getAccountId(), login.getEmailId(), login.getPassword());
					SendEmail(login.getEmailId());
					request.getSession().invalidate();
					return new ModelAndView(new RedirectView("/controller"));
				}
			}catch(Exception exc) {
				exc.printStackTrace();
				System.out.println("xxxxxxxxx Error at '/changePassword' in HomeController");
			}
		}
		return new ModelAndView("settings", "page", "changePSWD");
	}
	
	@RequestMapping(value = "/register")
	public ModelAndView register(@RequestParam(value = "role", required = false) String role, HttpServletRequest request) {
		try {
			 if(role.equals("U")) {
				 request.setAttribute("user", new User());
				 return new ModelAndView("register", "role", "U");
			 }else if(role.equals("M")){
//				 request.setAttribute("user", new Marketer());
				 return new ModelAndView("register", "role", "M");
			 }
		}catch(Exception exc) {
			exc.getStackTrace();
			System.out.println("xxxxxxxxx Error at '/register' in Home Controller");
		}
		return new ModelAndView(new RedirectView("/controller"));
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
		try {
			request.getSession().invalidate();
		}catch(Exception exc) {
			exc.getStackTrace();
			System.out.println("xxxxxxxxx Error at '/logout' in Home Controller");
		}
		return new ModelAndView(new RedirectView("/controller"));
	}
	
	public void SendEmail(String emailId) {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			//User your gmail username and password
			email.setAuthenticator(new DefaultAuthenticator("robertjack2112@gmail.com", "onepiece2112"));
			email.setSSLOnConnect(true);
			email.setFrom("robertjack2112@gmail.com");
			email.setSubject("Password Changed");
			email.setMsg("You just recently changed your password");
			email.addTo(emailId);
			email.send();
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}
}

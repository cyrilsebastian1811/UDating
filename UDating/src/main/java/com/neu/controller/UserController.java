package com.neu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.neu.edu.dao.LocationDao;
import com.neu.edu.dao.LoginDao;
import com.neu.edu.dao.MatchedDao;
import com.neu.edu.dao.MessageDao;
import com.neu.edu.dao.UserDao;
import com.neu.edu.model.Location;
import com.neu.edu.model.Login;
import com.neu.edu.model.Matched;
import com.neu.edu.model.Message;
import com.neu.edu.model.User;
import com.neu.validators.UserRegistrationValidator;

@Controller
public class UserController {
	@Autowired
	BCryptPasswordEncoder security;
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	MatchedDao matchedDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	UserRegistrationValidator userValidation;
	
	@Autowired
	MessageDao messageDao;
	
	private Gson gson = new Gson();
	
	@RequestMapping(value = "/userAccount")
	public ModelAndView SurroundingUsers(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("account");
		List<User> users = null;
		try {
			if(user.getRole() == 'U') {
//				String street = user.getLocation().getStreet();
//				String city = user.getLocation().getCity();
//				String country = user.getLocation().getCountry();
				users = userDao.getUsers(user.getId());
			}
		}catch(Exception exc) {
			exc.getStackTrace();
			System.out.println("xxxxxxxxx Error at /userAccount");
		}
		return new ModelAndView("useraccount","users",users);
	}
	
	@RequestMapping(value = "/userMessage")
	public ModelAndView Message(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("account");
		List<User> matched = null;
		try {
			if(user.getRole() == 'U') {
				matched = matchedDao.get(user);
			}
		}catch(Exception exc) {
			exc.getStackTrace();
			System.out.println("xxxxxxxxx Error at /userMessage");
		}
		return new ModelAndView("message","profiles",matched);
	}
	
	@RequestMapping(value = "/userSettings")
	public ModelAndView Settings(@ModelAttribute("newUser") User newUser,@RequestParam(value = "submit", required = false) String submit, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User oldUser = (User) session.getAttribute("account");
		try {
			if(submit.equals("update")) {
				update(oldUser, newUser);
				userDao.update(oldUser);
				return new ModelAndView(new RedirectView("userAccount"));
			}
		}catch(Exception exc) {
			exc.printStackTrace();
			System.out.println("xxxxxxxxx Error at /userSettings");
		}
		request.setAttribute("role", "U");
		return new ModelAndView("settings", "page", "settings");
	}
	
	public void update(User oldUser, User newUser) {
		if(newUser.getSexPreference() == 'M' || newUser.getSexPreference() == 'F') oldUser.setSexPreference(newUser.getSexPreference());
		if(newUser.getAgePreference()>=18 && newUser.getAgePreference()<=50) oldUser.setAgePreference(newUser.getAgePreference());
		if(!newUser.getBio().equals("")) oldUser.setBio(newUser.getBio());
		if(newUser.getPhoto()!=null) {
			String oldImageName = oldUser.getPhotoFilePath();
			if(oldImageName!=null) {
				File oldImage = new File("/Users/cyrilsebastian/WebToolsApps/"+oldImageName);
				oldImage.delete();
				System.out.println(oldImage);
			}
			oldUser.setPhoto(newUser.getPhoto());
			saveFile(oldUser);
		}
	}
	
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidation);
    }
	
	@RequestMapping(value = "/registerUser")
	public ModelAndView register(@Valid @ModelAttribute("user") User user, BindingResult errors, HttpServletRequest request) {
		try {
			System.out.println("In registerUser");
			if(!errors.hasErrors()) {
				user.setRole('U');
				prepareUser(user);
				userDao.save(user);
				return new ModelAndView(new RedirectView("/controller"));
			}
		}catch(Exception exc){
			exc.printStackTrace();;
			System.out.println("xxxxxxxxx Error at /registerUser");
		}
		return new ModelAndView("register","role","U");
	}

	private void prepareUser(User u) {
		saveFile(u);
		
		// hashing password 
		String actualPassword = u.getPassword();
		u.setPassword(security.encode(actualPassword));
		
		// setting the role of user to U
		u.setRole('U');
	}
	
	private void saveFile(User u) {
		if(u.getPhoto() != null && u.getPhoto().getSize()!=0) {
			String localPath = "/Users/cyrilsebastian/WebToolsApps/images/";
			String photoNewName = new Date().getTime() + "-" + u.getPhoto().getOriginalFilename().replace(" ", "_");
			String newPath = localPath+photoNewName;
			try {
				File file = new File(newPath);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(u.getPhoto().getBytes());
				fos.close();
				u.setPhotoFilePath("/images/"+photoNewName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

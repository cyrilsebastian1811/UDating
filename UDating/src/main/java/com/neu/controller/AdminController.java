package com.neu.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.neu.edu.dao.AdminDao;
import com.neu.edu.dao.MatchedDao;
import com.neu.edu.model.Admin;
import com.neu.edu.model.Advert;
import com.neu.edu.model.User;

@Controller
public class AdminController {
	@Autowired
	AdminDao adminDao;
	
	@RequestMapping(value = "/adminAccount")
	private ModelAndView showAdverts(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("account");

		if(admin!=null && admin.getRole() == 'A') {
			
			List<Advert> adverts = adminDao.getAdverts();
			return new ModelAndView("adminaccount","adverts",adverts);
		}else { 
			return new ModelAndView(new RedirectView("/edu",false));
		}
	}
}

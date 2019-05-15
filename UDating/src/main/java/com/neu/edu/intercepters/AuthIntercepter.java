package com.neu.edu.intercepters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.neu.edu.model.Login;

public class AuthIntercepter extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Login account = (Login) session.getAttribute("account");
		String uri = request.getRequestURI();
		uri = uri.substring(12, uri.length());
		
		if(account!=null && (uri.equals("/") || uri.equals("") || uri.startsWith("register"))) {
			System.out.println("I'm logged in and trying to access Authentication pages");
			if(account.getRole() == 'U') response.sendRedirect("/controller/userAccount");
			else response.sendRedirect("/controller/marketerAccount");
			return false;
		}else if(account==null && !uri.equals("/") && !uri.equals("") && !uri.startsWith("register") && !uri.startsWith("change")) {
			System.out.println("Can't access pages without logging in");
			response.sendRedirect("/controller");
			return false;
		}
		System.out.println("Your Logged in");
		return true;
	}

}

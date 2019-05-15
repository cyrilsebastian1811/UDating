package com.neu.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.neu.edu.dao.LocationDao;
import com.neu.edu.dao.LoginDao;
import com.neu.edu.dao.MatchedDao;
import com.neu.edu.dao.MessageDao;
import com.neu.edu.dao.UserDao;
import com.neu.edu.model.DisplayUser;
import com.neu.edu.model.Message;
import com.neu.edu.model.User;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserAjaxController {
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	MatchedDao matchedDao;
	
	@Autowired
	MessageDao messageDao;
	
	private Gson gson = new Gson();
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@RequestMapping(value="ajax/users/{userId}", method = RequestMethod.PUT)
	private String match(@PathVariable("userId") short userId, @RequestBody MultiValueMap<String, String> data, HttpServletRequest request) {
		short profileId = Short.parseShort(data.getFirst("profileId"));
		String clicked = data.getFirst("clicked");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("account");
		String message;
		try {
			boolean success = false;
			if(clicked.equals("like")) {
				success = matchedDao.addUsers(user.getId(), profileId);
				message = "You can start messaging him/her";
			}else if(clicked.equals("dislike")) {
				success = locationDao.removeUser(user.getId(), profileId);
				message = "You just disliked a profile";
			}else {
				message = "Reporting this profile";
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
			message = "code-tampering/Already Liked this user";
		}
		return this.gson.toJson(message);
	}
	
	@RequestMapping(value="ajax/messages", method = RequestMethod.GET)
	private String getMessages(@RequestParam("userId") short userId, @RequestParam("profileId") short profileId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("account");
		List<Message> messages = null;
		try {
			if(user!=null) messages = messageDao.get(userId, profileId, 0);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		return this.gson.toJson(messages);
	}
	
	@MessageMapping("/message")
	private void postMessage(Principal principal, String data) {
		Message message = this.gson.fromJson(data, Message.class);
		try {
			messageDao.save(message);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		messagingTemplate.convertAndSendToUser(message.getToId()+"", "/queue/reply", message);
	}

}

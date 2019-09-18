package org.spring.security.learn.chapter1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController {
	
	@RequestMapping({"/anon1","/anon2"})
	public String anon() {
		return "anon";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public String user(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("aa","xxxx");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		response.addCookie(cookie);
		return "user";
	}

}

package org.spring.security.learn.chapter1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String user() {
		return "user";
	}
	@PostMapping("/user1")
	@ResponseBody
	public String user1() {
		return "user1";
	}

}

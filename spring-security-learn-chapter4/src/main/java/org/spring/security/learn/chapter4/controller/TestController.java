package org.spring.security.learn.chapter4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zc.
 */
@Controller
public class TestController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	@RequestMapping("/login/submit")
	public String submit() {
		return "index";
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "user";
	}

}

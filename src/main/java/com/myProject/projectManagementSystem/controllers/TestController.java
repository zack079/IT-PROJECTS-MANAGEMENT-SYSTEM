package com.myProject.projectManagementSystem.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	@GetMapping("/test")
	public String home(HttpServletRequest rq) {
		System.out.println("STRING IS " +rq.getParameter("name"));
		return "test";
	}
	
}

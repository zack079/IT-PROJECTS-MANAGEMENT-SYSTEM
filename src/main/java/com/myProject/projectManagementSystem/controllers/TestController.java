package com.myProject.projectManagementSystem.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.Director;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.User;
import com.myProject.projectManagementSystem.services.DemandService;
import com.myProject.projectManagementSystem.services.DeveloperService;
import com.myProject.projectManagementSystem.services.DirectorService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;
import com.myProject.projectManagementSystem.services.UserService;
@Controller
public class TestController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DeveloperService developerService;
	@Autowired
	private DirectorService directorService;
	@Autowired
	private ProjectManagerService projectManagersService;
	@Autowired
	private DemandService demandService;
	@Autowired
	private UserService userService;
	/*****TEST***/
	@GetMapping("/test")
	public String test() {
		List<User> users=userService.getUsers();
		for(User user:users) {
			System.out.println(user);
		}
		return "test";
	}
	
	
	
	/*******TEST******/
}

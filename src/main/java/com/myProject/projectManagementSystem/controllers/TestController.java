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
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.services.DirectorService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;

@Controller
public class TestController {
	
	@Autowired
	private ProjectService projectservice;
	@Autowired
	private DirectorService directorService;
	@Autowired
	private ProjectManagerService projectManagersService;
	
	@GetMapping("/test")
	public String home() {
		
		
		return "test";
	}
	
}

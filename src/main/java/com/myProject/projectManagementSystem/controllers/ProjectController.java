package com.myProject.projectManagementSystem.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.services.ProjectService;


@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/tables-data")
	public String getProjects(Model model) {
		//List<Project> projects= projectService.getProjects();
		//model.addAttribute("projects",projects);
		return "tables-data";
	}
}

package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.services.DeveloperService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;


@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectManagerService projectManagerService;
	@Autowired
	private DeveloperService developerService;
	
	
	@GetMapping("/projects-table")
	public String getProjectsPage(Model model) {
		List<Project> projects= projectService.getProjects();
		model.addAttribute("projects",projects);
		return "projects-table";
	}
	
	@GetMapping("/add-project")
	public String addProjectPage(Model model) {
		List<ProjectManager> projectManagers =  projectManagerService.getProjectManagers();
		List<Developer> allDevelopers =  developerService.getDevelopers();
		List<Developer> developers = new ArrayList<Developer>();
		for(Developer developer: allDevelopers) {
			if(developer.getProject()==null) {
				System.out.println(developer);
				developers.add(developer);
			}
		}
		model.addAttribute("developers", developers);

		model.addAttribute("projectManagers", projectManagers);
		return "add-project";
	}
	
	@PostMapping("/add-project/addProject")
	public String addProject(Project project) {
		project.setState("en cours");
		projectService.addProject(project);
		return "redirect:/add-project";
	}
}

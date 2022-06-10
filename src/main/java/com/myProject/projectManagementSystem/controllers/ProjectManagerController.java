package com.myProject.projectManagementSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.services.ProjectManagerService;

@Controller
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService projectManagerService;
	
	//
	/*****
	 * 
	 * see a project manager info
	 */

	@GetMapping("project-manager-page")
	public String getDeveloperPage(@RequestParam int id,Model model) {
		int projectManagerID=id;
		try {
			ProjectManager projectManager = projectManagerService.getProjectManagerById(projectManagerID);
			model.addAttribute("projectManager", projectManager);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		
		return "project-manager-page";
		
	}
	/*****
	 * 
	 * see a project managers table of info
	 */
	

	@GetMapping("project-managers-table")
	public String getProjectManagerTablePage(Model model) {
	
		List<ProjectManager> projectManagers = projectManagerService.getProjectManagers();
		
		model.addAttribute("projectManagers", projectManagers);
	
		return "project-managers-table";
		
	}
	
}

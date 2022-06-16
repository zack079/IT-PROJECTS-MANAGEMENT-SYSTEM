package com.myProject.projectManagementSystem.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myProject.projectManagementSystem.models.Demand;
import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.services.DemandService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;

@Controller
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService projectManagerService;
	@Autowired
	private DemandService demandService;
	@Autowired
	private ProjectService projectService;
	
	
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
	
	
	/****
	 * 
	 * 
	 * Page that adds a project manager
	 * 
	 */
	
	@GetMapping("add-project-manager")
	public String addProjectManagerPage(Model model) {
		if (!model.containsAttribute("projectManager")) {
			model.addAttribute("projectManager",new ProjectManager());
	    }
		
		return "add-project-manager";
		
	}
	/****
	 * 
	 * 
	 * Handling adding a project manager
	 * 
	 */
	@PostMapping("project-manager-added")
	public String PprojectManagerAdded(@Valid @ModelAttribute("projectManager") ProjectManager projectManager,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {
		if(!bindingResult.hasErrors()) {
			
			
			String rawPassword = "pass";
			String encodedPassword= new BCryptPasswordEncoder().encode(rawPassword);
			projectManager.setPassword(encodedPassword);
			try {
				
				projectManagerService.addProjectManager(projectManager);
			}catch(Exception exception) {
				exception.printStackTrace();
				redirectAttributes.addFlashAttribute("projectManager",projectManager); 
				redirectAttributes.addFlashAttribute("UsernameAlreadyExists",true);
				redirectAttributes.addFlashAttribute("projectManagerNotAdded",true);
				return "redirect:add-project-manager";
			}
			
			
			redirectAttributes.addFlashAttribute("projectManagerAdded",true);
		}else {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.projectManager", bindingResult);   
			redirectAttributes.addFlashAttribute("projectManager",projectManager); 
			redirectAttributes.addFlashAttribute("projectManagerNotAdded",true);
		}
		
		return "redirect:add-project-manager";
		
		
	}
	
	/*
	 * 
	 * Delete projectManager account
	 * 
	 * 
	 */
	
	
	@GetMapping("/delete-projectmanager-account")
	public String deleteProjectManagerAccount(@RequestParam int id,RedirectAttributes redirectAttributes) {
		try {
			ProjectManager projectManager= projectManagerService.getProjectManagerById(id);
			List<Project> projects=projectManager.getProjects();
			projectManager.setProjects(null);
			for(Project project:projects) {
				project.setProjectManager(null);
				projectService.addProject(project);
			}
			List<Demand> demands=projectManager.getDemands();
			projectManager.setDemands(null);
			for(Demand demand:demands) {
				demandService.deleteDemand(demand.getDemandID());
			}
			projectManagerService.addProjectManager(projectManager);
			projectManagerService.deleteProjectManager(projectManager.getId());
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		redirectAttributes.addFlashAttribute("projectManagerAccountDeleted",true);
		
		return "redirect:project-managers-table";
	}
	
	
	
}

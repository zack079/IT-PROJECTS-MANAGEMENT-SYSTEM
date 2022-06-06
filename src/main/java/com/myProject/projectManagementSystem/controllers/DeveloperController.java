package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.services.DeveloperService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;

@Controller
public class DeveloperController {
	
	
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectManagerService projectManagerService;
	@Autowired
	private DeveloperService developerService;
	
	/********
	 * 
	 * deleting a developer from a project
	 * 
	 * 
	 * ***/
	
	
	@GetMapping("/delete-developer")
	public String deleteDeveloperFromProject(@RequestParam int id,RedirectAttributes redirectAttributes) {
		int projectID;
		try {	
			
			Developer developer =developerService.getDeveloperById(id);
			if(developer.getProject()!=null) {
				projectID=developer.getProject().getProjectID();
				redirectAttributes.addFlashAttribute("projectEdited",true);
				developer.setProject(null);
				developerService.addDeveloper(developer);
			}else {
				return "redirect:error";
			}
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		return "redirect:edit-project?id="+projectID;
	}
	
	
	@PostMapping("project-edit-addDevelopers")
	public String addDevelopersToProject(@RequestParam int projectID,@RequestParam List<Integer> freeDevelopers,RedirectAttributes redirectAttributes) {
		try {
			Project project =projectService.getProjectById(projectID);
			List<Developer> developersToAdd = new ArrayList<Developer>(); 
			//getting developers by id
			for(int developerId : freeDevelopers) {
				Developer developer = developerService.getDeveloperById(developerId);
				if(developer.getProject()==null) {
					developersToAdd.add(developer);
				}
			}
			//assigning developers to the project
			for(Developer developer : developersToAdd) {
				developer.setProject(project);
				developerService.addDeveloper(developer);
			}
			redirectAttributes.addFlashAttribute("projectEdited",true);
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		
		
		return "redirect:edit-project?id="+projectID;
		
	}
	
	
	
	@GetMapping("developer-page")
	public String getDeveloperPage(@RequestParam int id,Model model) {
		int DeveloperID=id;
		try {
			Developer developer = developerService.getDeveloperById(DeveloperID);
			model.addAttribute("developer", developer);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		
		return "developer-page";
		
	}
	
	
	
	@GetMapping("developers-table")
	public String getDeveloperTablePage(Model model) {
	
		List<Developer> developers = developerService.getDevelopers();
		
		model.addAttribute("developers", developers);
	
		return "developers-table";
		
	}

}

package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * deleteing a developer from a project
	 * 
	 * delete-developer
	 * 
	 * ***/
	
	
	@GetMapping("/delete-developer")
	public String deleteDeveloperFromProject(@RequestParam int id,Model model,RedirectAttributes redirectAttributes) {
		try {		
			Developer developer =developerService.getDeveloperById(id);
			redirectAttributes.addFlashAttribute("projectEdited",true);
			developer.setProject(null);
			developerService.addDeveloper(developer);
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:projects-table";
		}
		
		return "edit-project";
	}

}

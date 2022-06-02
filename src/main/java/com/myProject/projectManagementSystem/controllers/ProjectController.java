package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/******
	 * 
	 * Where to use redirect attributes
	 * 
	 * 
	 * *****/
	
	@GetMapping("/add-project")
	public String addProjectPage(Model model) {
		
		
		if (!model.containsAttribute("project")) {
			model.addAttribute("project",new Project());
	    }
		
		List<ProjectManager> projectManagers =  projectManagerService.getProjectManagers();//
		List<Developer> allDevelopers =  developerService.getDevelopers();
		List<Developer> developers = new ArrayList<Developer>();
		for(Developer developer: allDevelopers) {
			if(developer.getProject()==null) {
				developers.add(developer);
			}
		}
		model.addAttribute("developers", developers);
		model.addAttribute("projectManagers", projectManagers);
		return "add-project";
	}
	
	
	
	@PostMapping("/project-added")
	public String addProject(@Valid @ModelAttribute("project") Project project,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		
		if(!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("projectInserted",true);
			project.setState("en cours");
			projectService.addProject(project);
			for(Developer developer : project.getDevelopers()) {
				developer.setProject(project);
				developerService.addDeveloper(developer);
			}
		}else {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", bindingResult);
		   
			 redirectAttributes.addFlashAttribute("project",project); 
			 redirectAttributes.addFlashAttribute("projectNotInserted",true);
		}
		
		return "redirect:add-project";
	}
	
	/*********/
	
	

	@GetMapping("/project-added")
	public String addProgectGet() {
		return "redirect:add-project";

	}
	
	
	
	
	
	
	@GetMapping("/delete-project")
	public String deleteProject(@RequestParam int id) {
		List<Developer> developers = projectService.getProjectById(id).getDevelopers();
		for(Developer developer : developers) {
			developer.setProject(null);
			developerService.addDeveloper(developer);
		}
		projectService.deleteProject(id);
		return "redirect:projects-table";
	}
	
	
	
	@GetMapping("/projects-table")
	public String getProjectsPage(Model model) {
		List<Project> projects= projectService.getProjects();
		model.addAttribute("projects",projects);
		return "projects-table";
	}

	@GetMapping("/edit-project")
	public String editProject(@RequestParam int id,Model model) {
		try {
			Project project =projectService.getProjectById(id);
			model.addAttribute("project",project);

		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:projects-table";
		}
		
		return "add-project";
	}

}


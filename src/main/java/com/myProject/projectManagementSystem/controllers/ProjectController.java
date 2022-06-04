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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
	/****
	 * handling missing parameters in get requests
	 * 
	 * ****/
	 
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
	    String name = ex.getParameterName();
	    System.out.println("MissingParams:parameter is missing" +name );
	    // Actual exception handling
	    return "error";
	}
	
	/*****
	 * 
	 * form of creating a project
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
	
	/*****
	 * 
	 * Handling submitted project values
	 * 
	 * ***/
	
	
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
	
	
	
	/*****
	 * 
	 * Deleting a project
	 * 
	 * ******/
	
	
	@GetMapping("/delete-project")
	public String deleteProject(@RequestParam int id,RedirectAttributes redirectAttributes) {
		List<Developer> developers=new ArrayList<>();
		try {
			developers = projectService.getProjectById(id).getDevelopers();
		}catch(Exception e) {
			e.printStackTrace();
			return "redirect:projects-table";
		}
		for(Developer developer : developers) {
			developer.setProject(null);
			developerService.addDeveloper(developer);
		}
		projectService.deleteProject(id);	
		redirectAttributes.addFlashAttribute("projectDeleted",true);
		
		return "redirect:projects-table";
	}
	
	
	
	/*****
	 * showing projects
	 * ******/
	
	@GetMapping("/projects-table")
	public String getProjectsPage(Model model) {
		List<Project> projects= projectService.getProjects();
		model.addAttribute("projects",projects);
		return "projects-table";
	}
	
	
	
	/*****
	 * 
	 * Editing a project
	 * 
	 * 
	 * ******/

	@GetMapping("/edit-project")
	public String editProjectPage(@RequestParam int id,Model model) {
		try {		
			Project project =projectService.getProjectById(id);
			List<Developer> allDevelopers= developerService.getDevelopers();
			List<Developer> freeDevelopers = new ArrayList<Developer>();
			for(Developer developer : allDevelopers) {
				if(developer.getProject()==null) {
					freeDevelopers.add(developer);
					System.out.println(developer);
				}
			}
			model.addAttribute("developer",new Developer());
			model.addAttribute("freeDevelopers",freeDevelopers);
			model.addAttribute("project",project);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:projects-table";
		}
		
		return "edit-project";
	}
	
	
	/******
	 * 
	 * edited project error page
	 * in case info of edited project are invalid
	 * 
	 * *******/
	
	@GetMapping("/edit-project-error")
	public String editProjectErrorPage(Model model) {
		
		return "edit-project";
	}


	/***
	 * 
	 * Handling changed project info (name,....)
	 * 
	 * ***/
	
	
	@PostMapping("/edit-project")
	public String editProject(@Valid @ModelAttribute("project") Project project,BindingResult bindingResult,RedirectAttributes redirectAttributes) {

		if(!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("projectEdited",true);
			Project editedProject=new Project();
			try {
				editedProject = projectService.getProjectById(project.getProjectID());
			}catch(Exception e) {
				e.printStackTrace();
				return "redirect:error";//it used to be edit-project (don't know why)
			}
			editedProject.setTitle(project.getTitle());
			editedProject.setType(project.getType());
			editedProject.setDescription(project.getDescription());
			editedProject.setStart_date(project.getStart_date());
			editedProject.setDuration(project.getDuration());
			editedProject.setState(project.getState());
			projectService.addProject(editedProject);
		}else {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", bindingResult);   
			redirectAttributes.addFlashAttribute("project",project); 
			redirectAttributes.addFlashAttribute("projectNotEdited",true);
			return "redirect:edit-project-error";
		}
		
		return "redirect:edit-project?id="+project.getProjectID();
	}
	
	
	
	
	
}


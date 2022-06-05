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
		}catch(Exception exception) {
			exception.printStackTrace();
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
			List<ProjectManager> projectManagers = projectManagerService.getProjectManagers();
			for(Developer developer : allDevelopers) {
				if(developer.getProject()==null) {
					freeDevelopers.add(developer);
				}
			}
			model.addAttribute("projectManagers", projectManagers);
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
	 * in case info of edited project is invalid
	 * 
	 * *******/
	
	@GetMapping("/edit-project-error")
	public String editProjectErrorPage(@RequestParam int id,Model model) {
		try {		
			//project is retrieved to show other sections other than project info (titre,descrption...)
			// no need to pass project to model as it's passed as redirectAttribute (to show binding errors)
			Project project =projectService.getProjectById(id);
			List<Developer> allDevelopers= developerService.getDevelopers();
			List<ProjectManager> projectManagers = projectManagerService.getProjectManagers();
			List<Developer> freeDevelopers = new ArrayList<Developer>();
			
			for(Developer developer : allDevelopers) {
				if(developer.getProject()==null) {
					freeDevelopers.add(developer);
				}
			}
			
			/****
			 * 
			 */
			model.addAttribute("projectManagers", projectManagers);
			model.addAttribute("developer",new Developer());
			model.addAttribute("freeDevelopers",freeDevelopers);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:projects-table";
		}
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
				return "redirect:error";
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
			//pass the project id to be used sections other than project info 
			return "redirect:edit-project-error?id="+project.getProjectID();
		}
		
		return "redirect:edit-project?id="+project.getProjectID();
	}
	
	
	/*******
	 * 
	 * Handling deleting project manager
	 * 
	 */
	
	@GetMapping("/delete-projectManager")
	public String deleteDeveloperFromProject(@RequestParam int id,RedirectAttributes redirectAttributes) {
		int projectID=id;
		try {	
			
			Project project  = projectService.getProjectById(projectID);
			
			if(project.getProjectManager()!=null) {
				project.setProjectManager(null);
				redirectAttributes.addFlashAttribute("projectEdited",true);
				projectService.addProject(project);
			}else {
				return "redirect:error";
			}
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		return "redirect:edit-project?id="+projectID;
	}
	
	/******
	 * 
	 * Handling adding a project Manager to a project
	 * 
	 */
	
	@PostMapping("project-edit-addProjectManager")
	public String addProjectManagerToProject(@RequestParam int projectID,@RequestParam int projectManager,RedirectAttributes redirectAttributes) {
		int projectManagerId=projectManager;
		try {
			
			Project project =projectService.getProjectById(projectID);
			//retrieving project manager by id
			ProjectManager projectManagerToAdd = projectManagerService.getProjectManagerById(projectManagerId);
			if(project.getProjectManager()==null) {
				//assigning project manager to the project
				project.setProjectManager(projectManagerToAdd);
				projectService.addProject(project);
			}
			
			redirectAttributes.addFlashAttribute("projectEdited",true);
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		
		
		return "redirect:edit-project?id="+projectID;
		
	}
	
	
}


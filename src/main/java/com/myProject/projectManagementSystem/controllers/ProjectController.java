package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.myProject.projectManagementSystem.models.Director;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.models.User;
import com.myProject.projectManagementSystem.security.UserPrincipal;
import com.myProject.projectManagementSystem.services.DeveloperService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;
import com.myProject.projectManagementSystem.services.UserService;


@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectManagerService projectManagerService;
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private UserService userService;
	//
	

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
		//getting the logged in user
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		Director director=null;
		if(user instanceof Director) {
			director =(Director) user;
		}
		if(!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("projectInserted",true);
			project.setState("en cours");
			project.setProjectFinished(false);
			project.setDirector(director);
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
	//TODO: check if showing a director's projects creates a problem or not(no bugs till now)
	
	@GetMapping("/projects-table")
	public String getProjectsPage(Model model) {
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		Director director=null;
		Developer developer=null;
		ProjectManager projectManger=null;
		List<Project> allProjects=new ArrayList<Project>();
		if(user instanceof Director) {
			director =(Director) user;
			allProjects=director.getProjects();
		}else if(user instanceof ProjectManager) {
			projectManger=(ProjectManager)user;
			allProjects=projectManger.getProjects();
		}else if(user instanceof Developer) {
			developer=(Developer)user;
			if(developer.getProject()!=null) {
				allProjects.add(developer.getProject());
			}
		}
		
		List<Project> currentProjects=new ArrayList<Project>();
		
		for(Project project : allProjects) {
			if(!project.getProjectFinished()) {
				currentProjects.add(project);
			}
		}
		model.addAttribute("projects",currentProjects);
		return "projects-table";
	}
	
	
	
	/*****
	 * 
	 * Editing a project
	 * 
	 * 
	 * ******/
	//TODO: add the possibility to change projects's states for project managers.
	
	@GetMapping("/edit-project")
	public String editProjectPage(@RequestParam int id,Model model) {
		try {		
			Project project =projectService.getProjectById(id);
			int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
			User user=userService.getUserById(userID);
			ProjectManager projectManager=null;
			if(user instanceof ProjectManager) {
				projectManager=(ProjectManager)user;
				//in case the logged project manager doesn't manage the selected project an exception is thrown
				if(!projectManager.getProjects().contains(project)) {
					throw new Exception();
				}
			}else {
				//the user is a director
			}
			
			
			//in case the passed project is finished
			if(project.getProjectFinished()) {
				throw new Exception();
			}
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
			return "redirect:error";
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
	
	/*********
	 * 
	 * Handling finishing a project
	 * 
	 */
	
	@GetMapping("/finish-project")
	public String finishingProject(@RequestParam int id,RedirectAttributes redirectAttributes) {
		
		
		int projectID=id;
		try {	
			//if a project manager is logged in,he can only finish projects that he manages
			Project project = projectService.getProjectById(projectID);
			int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
			User user=userService.getUserById(userID);
			ProjectManager projectManager=null;
			if(user instanceof ProjectManager) {
				projectManager=(ProjectManager)user;
				//in case the logged project manager doesn't manage the selected project an exception is thrown
				if(!projectManager.getProjects().contains(project)) {
					throw new Exception();
				}
			}else {
				//the user is a director
			}

			project.setProjectFinished(true);
			project.setEnd_date(new Date());
			projectService.addProject(project);
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		return "redirect:projects-table";
	}
	
	
	
	
	/***********
	 * 
	 * 
	 * showing finished projects table
	 * 
	 */
	@GetMapping("/old-projects-table")
	public String getOldProjectsTable(Model model) {
		List<Project> allProjects= projectService.getProjects();
		List<Project> finishedProjects=new ArrayList<Project>();
		
		for(Project project : allProjects) {
			if(project.getProjectFinished()) {
				finishedProjects.add(project);
			}
		}
		model.addAttribute("projects",finishedProjects);
		return "old-projects-table";
	}
	
	
}


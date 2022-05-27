package com.myProject.projectManagementSystem.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.Director;
import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.services.DirectorService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;

@Controller
public class TestController {
	
	@Autowired
	private ProjectService projectservice;
	@Autowired
	private DirectorService directorService;
	@Autowired
	private ProjectManagerService projectManagersService;
	
	@GetMapping("/test")
	public String home() {
		/*Date date= new Date();
		ProjectManager projectManger= new ProjectManager("firstname1", "lastname2", date, null, null, null, date);
		Director director = new Director("firsnmae1", "directro last name2", date, null, null, null);
		//    public Project(String title, String description, String type, Date start_date, String state, int duration, ProjectManager projectManager, ArrayList<Developer> developers, Director director) {
		Project project= new Project("Project management system1","allows direcotr to..1","web",date,"en cour",20,projectManger,director);
		projectManagersService.addProjectManager(projectManger);
		directorService.addDirector(director);
		projectservice.addProject(project);
		*/
		List<Project> projects = projectservice.getProjects();
		for(Project project : projects) {
			System.out.println("project ID : " + project.getProjectID());
			System.out.println("title :" + project.getTitle());
			System.out.println("descipriotn: " +project.getDescription());
			System.out.println("direcotrs name = "+ project.getDirector().getFirstname());
			System.out.println("project manager name = "+ project.getProjectManager().getFirstname());
			System.out.println("------------------------");
			
		}
		
		return "test";
	}
	
}

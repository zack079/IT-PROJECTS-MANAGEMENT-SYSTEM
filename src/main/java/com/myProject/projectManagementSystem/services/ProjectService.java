package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public List<Project> getProjects(){
		List<Project> projects= new ArrayList<Project>();
		projectRepository.findAll().forEach(projects::add);
		return projects;
	}
	
	public void addProject(Project project) {
		projectRepository.save(project); 
	}

}

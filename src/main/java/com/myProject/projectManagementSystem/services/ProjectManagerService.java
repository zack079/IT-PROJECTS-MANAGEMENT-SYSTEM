package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.repositories.ProjectManagerRepository;

@Service
public class ProjectManagerService {
	@Autowired
	private ProjectManagerRepository projectManagerRepository;
	
	public List<ProjectManager> getProjectManagers(){
		List<ProjectManager> projectManagers= new ArrayList<ProjectManager>();
		projectManagerRepository.findAll().forEach(projectManagers::add);
		return projectManagers;
	}
	
	public void addProjectManager(ProjectManager projectManager) {
		projectManagerRepository.save(projectManager); 
	}

}

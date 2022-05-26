package com.myProject.projectManagementSystem.services;

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
		return projectRepository.findAll();
	}

}

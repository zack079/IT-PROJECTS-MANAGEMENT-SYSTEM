package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.repositories.DeveloperRepository;

@Service
public class DeveloperService {
	@Autowired
	private DeveloperRepository developerRepository;
	
	public List<Developer> getDevelopers(){
		List<Developer> developers= new ArrayList<Developer>();
		developerRepository.findAll().forEach(developers::add);
		return developers;
	}
	
	public void addDeveloper(Developer developer) {
		developerRepository.save(developer); 
	}
	
	public void deleteDeveloper(Integer id) {
		developerRepository.deleteById(id);
	}

	public Developer getDeveloperById(Integer id) {
		Optional<Developer> optinalDeveloper = developerRepository.findById(id);
		Developer developer = optinalDeveloper.get();
		return developer;
	}
}

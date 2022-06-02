package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.Director;
import com.myProject.projectManagementSystem.repositories.DirectorRepository;

@Service
public class DirectorService {
	@Autowired
	private DirectorRepository directorRepository;
	
	public List<Director> getDirectors(){
		List<Director> directors= new ArrayList<Director>();
		directorRepository.findAll().forEach(directors::add);
		return directors;
	}
	
	public void addDirector(Director director) {
		directorRepository.save(director); 
	}
	
	public void deleteDirector(Integer id) {
		directorRepository.deleteById(id);
	}

	public Director getDirectorById(Integer id) {
		Optional<Director> optinalDirector = directorRepository.findById(id);
		Director director = optinalDirector.get();
		return director;
	}
}

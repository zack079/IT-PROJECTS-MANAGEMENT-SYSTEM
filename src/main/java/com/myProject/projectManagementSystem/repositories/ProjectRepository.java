package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.myProject.projectManagementSystem.models.Project;



@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	 
}

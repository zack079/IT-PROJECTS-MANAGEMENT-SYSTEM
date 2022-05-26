package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.projectManagementSystem.models.ProjectManager;
import org.springframework.stereotype.Repository;
@Repository
public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Integer> {

}

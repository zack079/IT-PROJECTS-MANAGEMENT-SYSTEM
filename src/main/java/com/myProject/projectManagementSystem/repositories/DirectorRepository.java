package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.projectManagementSystem.models.Director;
import org.springframework.stereotype.Repository;
@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

}

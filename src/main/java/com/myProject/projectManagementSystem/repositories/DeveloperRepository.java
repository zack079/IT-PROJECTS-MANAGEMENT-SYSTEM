package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.projectManagementSystem.models.Developer;

import org.springframework.stereotype.Repository;
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

}

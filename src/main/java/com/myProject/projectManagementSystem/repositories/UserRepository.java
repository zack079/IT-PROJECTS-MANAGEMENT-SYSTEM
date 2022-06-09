package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.projectManagementSystem.models.User;

import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}

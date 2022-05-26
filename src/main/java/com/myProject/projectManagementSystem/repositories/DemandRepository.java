package com.myProject.projectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myProject.projectManagementSystem.models.Demand;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Integer> {

}

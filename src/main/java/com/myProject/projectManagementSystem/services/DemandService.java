package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.Demand;
import com.myProject.projectManagementSystem.repositories.DemandRepository;

@Service
public class DemandService {
	
	@Autowired
	private DemandRepository demandRepository;
	
	public List<Demand> getDemands(){
		List<Demand> demands= new ArrayList<Demand>();
		demandRepository.findAll().forEach(demands::add);
		return demands;
	}
	
	public void addDemand(Demand demand) {
		demandRepository.save(demand); 
	}

}

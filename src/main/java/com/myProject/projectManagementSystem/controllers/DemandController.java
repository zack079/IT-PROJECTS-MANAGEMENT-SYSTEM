package com.myProject.projectManagementSystem.controllers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myProject.projectManagementSystem.models.Demand;
import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.ProjectManager;
import com.myProject.projectManagementSystem.models.User;
import com.myProject.projectManagementSystem.security.UserPrincipal;
import com.myProject.projectManagementSystem.services.DemandService;
import com.myProject.projectManagementSystem.services.DeveloperService;
import com.myProject.projectManagementSystem.services.ProjectManagerService;
import com.myProject.projectManagementSystem.services.ProjectService;
import com.myProject.projectManagementSystem.services.UserService;

@Controller
public class DemandController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectManagerService projectManagerService;
	@Autowired
	private DeveloperService developerService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private DemandService demandService;
	
	@GetMapping("/add-demand")
	public String addDemand(Model model) {
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		Developer developer=null;
		if(user instanceof Developer) {
			developer=(Developer)user;
			if(developer.getProject()==null) {
				model.addAttribute("hasNoProject",true);
				return "add-demand";
			}else if(developer.getProject().getProjectManager()==null) {
				model.addAttribute("hasNoProject",true);
				return "add-demand";
			}
		}
		
		if (!model.containsAttribute("demand")) {
			model.addAttribute("demand",new Demand());
	    }
		
		
		return "add-demand";
	}
	
	@PostMapping("/demand-added")
	public String addDemand(@Valid @ModelAttribute("demand") Demand demand,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		//getting the logged in user
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		Developer developer=null;
		if(user instanceof Developer) {
			developer=(Developer)user;
		}
		
		if(!bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("demandCreated",true);
			demand.setState("en cours");
			demand.setDeveloper(developer);
			demand.setDate(new Date());
			demand.setProjectManager(developer.getProject().getProjectManager());
			demandService.addDemand(demand);
		}else {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.demand", bindingResult);
		   
			 redirectAttributes.addFlashAttribute("demand",demand); 
			 redirectAttributes.addFlashAttribute("demandNotCreated",true);
		}
		
		return "redirect:add-demand";
	}
	/***
	 * 
	 * page that lets developer see his demand
	 * 
	 */
	
	@GetMapping("/demands-table")
	public String seeDemands(Model model) {
		//getting the logged in user
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		Developer developer=null;
		ProjectManager projectManager=null;
		if(user instanceof Developer) {
			developer=(Developer)user;
			model.addAttribute("demands", developer.getDemands());
		}
		if(user instanceof ProjectManager) {
			projectManager=(ProjectManager)user;
			model.addAttribute("demands", projectManager.getDemands());
		}
	
		return "demands-table";
	}
	
	
	/**
	 * page of editing the demand's state for a project manager
	 * 
	 */
	
	

	@GetMapping("/edit-demand")
	public String editDemandPage(@RequestParam int id,Model model) {
		try {		
			Demand demand =demandService.getDemandById(id);
			int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
			User user=userService.getUserById(userID);
			ProjectManager projectManager=null;
			if(user instanceof ProjectManager) {
				projectManager=(ProjectManager)user;
				//in case the logged project manager doesn't have the selected demand an exception is thrown
				if(!projectManager.getDemands().contains(demand)) {
					throw new Exception();
				}
			}else {
				//the user isn't a project manager
					throw new Exception();
			}
			
		
			
			model.addAttribute("demand", demand);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		return "edit-demand";
	}
	
	/***
	 * 
	 * 
	 * 
	 */
	
	
	@PostMapping("edit-demand-state")
	public String editProjectState(@RequestParam int demandID,@RequestParam String state,RedirectAttributes redirectAttributes) {

		if(state.length()>=1) {
			redirectAttributes.addFlashAttribute("demandEdited",true);
			Demand editedDemand=new Demand();
			try {
				editedDemand = demandService.getDemandById(demandID);
			}catch(Exception e) {
				e.printStackTrace();
				return "redirect:error";
			}
			editedDemand.setState(state);
			demandService.addDemand(editedDemand);
		}else {
			redirectAttributes.addFlashAttribute("demandNotEdited",true);
		}
		
		return "redirect:edit-demand?id="+demandID;
	
	}
	
	/*****
	 * 
	 * Deleting a demand
	 * 
	 * ******/
	
	
	@GetMapping("/delete-demand")
	public String deleteDemand(@RequestParam int id,RedirectAttributes redirectAttributes) {
		try {
			demandService.deleteDemand(id);	
		}catch(Exception exception) {
			exception.printStackTrace();
			return "redirect:error";
		}
		
		redirectAttributes.addFlashAttribute("demandDeleted",true);
		
		return "redirect:demands-table";
	}
	
	
	

}

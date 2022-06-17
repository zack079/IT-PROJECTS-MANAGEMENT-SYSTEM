package com.myProject.projectManagementSystem.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myProject.projectManagementSystem.models.Developer;
import com.myProject.projectManagementSystem.models.User;
import com.myProject.projectManagementSystem.security.UserPrincipal;
import com.myProject.projectManagementSystem.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("profile")
	public String getProfile(Model model) {
		
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User user=userService.getUserById(userID);
		model.addAttribute("user", user);
		return "profile";
	}
	/***
	 * 
	 * 
	 * 
	 */
	
	
	@PostMapping("change-user-info")
	public String userInfoChanged(@Valid @ModelAttribute("user") User userInfo,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {
		if(!bindingResult.hasErrors()) {
			int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
			User LoggedUser=userService.getUserById(userID);
			try {
				LoggedUser.setAddress(userInfo.getAddress());
				LoggedUser.setCity(userInfo.getCity());
				LoggedUser.setEmail(userInfo.getEmail());
				LoggedUser.setLastname(userInfo.getLastname());
				LoggedUser.setFirstname(userInfo.getFirstname());
				userService.addUser(LoggedUser);
			}catch(Exception exception) {
				exception.printStackTrace();
				redirectAttributes.addFlashAttribute("user",userInfo); 
				redirectAttributes.addFlashAttribute("infoNotChanged",true);
				return "redirect:profile";
			}
			
			redirectAttributes.addFlashAttribute("infoChanged",true);
		}else {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);   
			redirectAttributes.addFlashAttribute("user",userInfo); 
			redirectAttributes.addFlashAttribute("infoChanged",true);
		}
		
		return "redirect:profile";
		
		
	}
	
	
	@PostMapping("change-user-password")
	public String changeUserPassword(@RequestParam String password,@RequestParam String newpassword,@RequestParam String renewpassword,RedirectAttributes redirectAttributes) {
		String rawPassword=password;
		int userID=((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
		User LoggedUser=userService.getUserById(userID);
		String hashedPassword=LoggedUser.getPassword();
		if(BCrypt.checkpw(rawPassword,hashedPassword)) {
			if(newpassword.equals(renewpassword)) {
				String encodedPassword= new BCryptPasswordEncoder().encode(newpassword);
				LoggedUser.setPassword(encodedPassword);
				userService.addUser(LoggedUser);
				redirectAttributes.addFlashAttribute("passwordChanged",true);
			}else {
				redirectAttributes.addFlashAttribute("passowrdsNotMatched",true);
				return "redirect:profile";
				
			}
		}else {
			redirectAttributes.addFlashAttribute("passowrdIncorrect",true);
			return "redirect:profile";
		}
		return "redirect:profile";
	}
}

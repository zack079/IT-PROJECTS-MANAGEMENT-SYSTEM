package com.myProject.projectManagementSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myProject.projectManagementSystem.models.Project;
import com.myProject.projectManagementSystem.models.User;
import com.myProject.projectManagementSystem.repositories.ProjectRepository;
import com.myProject.projectManagementSystem.repositories.UserRepository;
import com.myProject.projectManagementSystem.security.UserPrincipal;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getUsers(){
		List<User> users= new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
	public void addUser(User user) {
		userRepository.save(user); 
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User getUserById(Integer id) {
		Optional<User> optinalUser = userRepository.findById(id);
		User user = optinalUser.get();
		return user;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found");
		}
		UserPrincipal userPrincipal = new UserPrincipal(user);
		
		return userPrincipal;
	}
	
}

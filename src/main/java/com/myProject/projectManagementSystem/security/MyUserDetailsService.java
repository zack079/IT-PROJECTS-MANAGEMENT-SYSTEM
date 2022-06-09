//package com.myProject.projectManagementSystem.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.myProject.projectManagementSystem.models.User;
//import com.myProject.projectManagementSystem.repositories.UserRepository;
//import com.myProject.projectManagementSystem.services.DirectorService;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
////	@Autowired
////	private UserRepository userRepository;
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		User user = userRepository.findByUsername(username);
////		if(user==null) {
////			throw new UsernameNotFoundException("user not found");
////		}
////		UserPrincipal userPrincipal = new UserPrincipal(user);
////		
////		return userPrincipal;
////	}
//
//}

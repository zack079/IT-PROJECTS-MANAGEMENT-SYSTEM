package com.myProject.projectManagementSystem.security;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.myProject.projectManagementSystem.services.DirectorService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		return daoAuthenticationProvider;
	}
	
	
	
//	
//	
//	private final PasswordEncoder passwordEncoder;
//
//	@Autowired
//	public AppSecurityConfig(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder=passwordEncoder;
//	}
//	
//	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails test = User.builder() 
//				.username("test")
//				.password(passwordEncoder.encode("password"))
//				.roles("DIRECTOR")
//				.build();
//		List<UserDetails> tests=new ArrayList<UserDetails>();
//		tests.add(test);
//		return new InMemoryUserDetailsManager(tests);
//	}
	
	
	
}

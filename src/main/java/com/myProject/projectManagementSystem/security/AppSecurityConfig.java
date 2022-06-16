package com.myProject.projectManagementSystem.security;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.myProject.projectManagementSystem.services.DirectorService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailsService;
/****
 * 
 * 
 * this part works, made by telusko
 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 	.antMatchers("/add-project",
		 				"/project-managers-table",
		 				"/project-manager-page",
		 				"/delete-project",
		 				"/edit-project-error",
		 				"/add-project-manager",
		 				"/delete-projectManager",
		 				"/delete-projectmanager-account").hasRole("DIRECTOR")
		 	.antMatchers("/developer-page",
		 				"/developers-table",
		 				"/add-developer",
		 				"/delete-developer",
		 				"/edit-project",
		 				"/old-projects-table",
		 				"/delete-developer-account",
		 				"/finish-project").hasAnyRole("DIRECTOR","PROJECTMANAGER")
		 	.antMatchers("/add-demand").hasRole("DEVELOPER")
		 	.antMatchers("/demands-table","/delete-demand").hasAnyRole("DEVELOPER","PROJECTMANAGER")
		 	.antMatchers("/edit-demand").hasRole("PROJECTMANAGER")
		 	.and()
		 	.formLogin().permitAll().defaultSuccessUrl("/index")
		 	.and()
		 	.authorizeRequests()
		 	.anyRequest().authenticated()
		 	.and()
		 	.logout().permitAll();
		         
		 
		 /*
		  .antMatchers("/*").hasRole("DIRECTOR")
		         .antMatchers("/index").hasRole("DIRECTOR")
		         .antMatchers("/index").hasRole("DIRECTOR")
		         .and().formLogin();
		
		  * */
		
		

	
	}

	
	
	
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.and()
			.logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	*/
	
	
//	
//	

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

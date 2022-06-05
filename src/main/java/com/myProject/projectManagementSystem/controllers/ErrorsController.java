package com.myProject.projectManagementSystem.controllers;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorsController {
	
	/****
	 * handling missing parameters in get requests
	 * 
	 * ****/
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
	    String name = ex.getParameterName();
	    System.out.println("MissingParams:parameter is missing" +name );
	    // Actual exception handling
	    return "error";
	}
	

}

package com.logesh.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209011131160547244L;

	public ProjectNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}

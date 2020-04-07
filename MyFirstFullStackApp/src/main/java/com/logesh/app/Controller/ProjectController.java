package com.logesh.app.Controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logesh.app.Service.ProjectService;
import com.logesh.app.Service.RequestValidationService;
import com.logesh.app.domain.Project;
import com.logesh.app.exceptions.ProjectIdentifierException;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private RequestValidationService requestValidationService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

		ResponseEntity<?> errors = requestValidationService.mapRequestValidation(result);
		if (errors != null)
			return errors;
		
		Project project1 = projectService.saveOrUpdateProject(project);

		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId){
		
		Project project=projectService.findProjectById(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdentifierException(" Project Identifier '" + projectId.toUpperCase() + "' does not exist");
		}
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {return projectService.findAllProjects();}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId) {
		
		projectService.deleteProjectById(projectId.toUpperCase());
		
		return new ResponseEntity<String>("Project "+projectId.toUpperCase()+" was deleted Succesfully",HttpStatus.OK);
	}
}

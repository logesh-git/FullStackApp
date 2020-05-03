package com.logesh.app.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logesh.app.Service.ProjectTaskService;
import com.logesh.app.Service.RequestValidationService;
import com.logesh.app.domain.Project;
import com.logesh.app.domain.ProjectTask;
import com.logesh.app.exceptions.ProjectNotFoundException;
import com.logesh.app.repositories.ProjectRepository;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private RequestValidationService requestValidationService;

	@Autowired
	private ProjectRepository projectRepository;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {

		ResponseEntity<?> errorMap = requestValidationService.mapRequestValidation(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {

		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID '" + backlog_id + "' doesn't exist");
		}
		return projectTaskService.findBacklogById(backlog_id);

	}

	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {

		return new ResponseEntity<ProjectTask>(projectTaskService.findPTByProjectSequence(backlog_id, pt_id),
				HttpStatus.OK);

	}

	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updateProjectTask, BindingResult result,
			@PathVariable String backlog_id, @PathVariable String pt_id) {

		ResponseEntity<?> errorMap = requestValidationService.mapRequestValidation(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask projectTask = projectTaskService.updateByProjectSequence(updateProjectTask, backlog_id, pt_id);

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);

	}

	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {

		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);

		return new ResponseEntity<String>(" Project Task " + pt_id + " was deleted Successfully", HttpStatus.OK);

	}
}

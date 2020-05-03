package com.logesh.app.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logesh.app.domain.Backlog;
import com.logesh.app.domain.ProjectTask;
import com.logesh.app.exceptions.ProjectNotFoundException;
import com.logesh.app.repositories.BacklogRepository;
import com.logesh.app.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {

			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			projectTask.setBacklog(backlog);

			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;

			backlog.setPTSequence(backlogSequence);

			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}

			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}

			projectTaskRepository.save(projectTask);
			return projectTask;
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {

		// checking existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project Not Found");
		}

		// checking existing project task
		ProjectTask projTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projTask == null) {
			throw new ProjectNotFoundException("Project Task with Id '" + pt_id + "' Not Found");
		}

		// checking project task's project identifier is same as backlog_id
		if (!projTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task with Id '" + pt_id + "' doesn't exist in project '" + backlog_id + "'");
		}

		return projTask;

	}
	
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		
		ProjectTask projectTask= findPTByProjectSequence(backlog_id,pt_id);
		
		projectTask=updatedTask;
		
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		
		ProjectTask projectTask= findPTByProjectSequence(backlog_id,pt_id);
		 projectTaskRepository.delete(projectTask);
	}
		
	
}

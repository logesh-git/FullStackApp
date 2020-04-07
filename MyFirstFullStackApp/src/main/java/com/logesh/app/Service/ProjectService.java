package com.logesh.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.logesh.app.domain.Project;
import com.logesh.app.exceptions.ProjectIdentifierException;
import com.logesh.app.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdentifierException(
					" Project Identifier " + project.getProjectIdentifier().toUpperCase() + " already exist");
		}
	}

	public Project findProjectById(String projectId) {

		return projectRepository.findByProjectIdentifier(projectId);

	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectById(String projectId) {

		Project project = projectRepository.findByProjectIdentifier(projectId);

		if (project == null) {
			throw new ProjectIdentifierException(
					" Cannot delete " + projectId.toUpperCase() + ". The Project doesn't exist.");
		}

		projectRepository.delete(project);

	}

}

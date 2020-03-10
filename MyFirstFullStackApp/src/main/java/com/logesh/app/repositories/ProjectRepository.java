package com.logesh.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.logesh.app.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	
}

package com.devpilot.ai.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Project;
import com.devpilot.ai.enums.ProjectStatus;
import com.devpilot.ai.projections.ProjectProjection;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<ProjectProjection> findAllProjectedBy();
	
	long countByStatus(ProjectStatus status);

}
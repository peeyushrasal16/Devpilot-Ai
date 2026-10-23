package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Documentation;

public interface DocumentationRepository extends JpaRepository<Documentation, Long> {

	// change the code: delete all documentations for project
	void deleteByProject_Id(Long projectId);

}
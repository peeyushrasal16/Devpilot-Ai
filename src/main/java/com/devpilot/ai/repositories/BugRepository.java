package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Bug;
import com.devpilot.ai.enums.BugPriority;
import com.devpilot.ai.enums.BugStatus;

public interface BugRepository extends JpaRepository<Bug, Long> {
	
	long countByStatus(BugStatus status);
	
	long countByPriority(BugPriority priority);

	// change the code: delete all bugs for project
	void deleteByProject_Id(Long projectId);

}
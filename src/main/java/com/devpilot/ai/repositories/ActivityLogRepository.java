package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

	// change the code: delete all activity logs for project
	void deleteByProject_Id(Long projectId);

}
package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.TaskAssignment;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
	
	long countByUser_Id(Long userId);
	
	void deleteByTask_Id(Long taskId);

	// change the code: delete assignments when project member is removed
	void deleteByUser_IdAndTask_Project_Id(Long userId, Long projectId);

}
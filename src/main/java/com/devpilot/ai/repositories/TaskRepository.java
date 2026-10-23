package com.devpilot.ai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.Task;
import com.devpilot.ai.enums.TaskPriority;
import com.devpilot.ai.enums.TaskStatus;
//Step 3 check reposiotores 
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	long countByStatus(TaskStatus status);

    long countByPriority(TaskPriority priority);

    // change the code: find and delete tasks by project
    java.util.List<Task> findByProject_Id(Long projectId);

    // change the code: delete all tasks for project
    void deleteByProject_Id(Long projectId);

}
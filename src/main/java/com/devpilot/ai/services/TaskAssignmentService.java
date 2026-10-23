package com.devpilot.ai.services;

import java.util.List;

import com.devpilot.ai.dtos.TaskAssignmentDto;

public interface TaskAssignmentService {

    TaskAssignmentDto assignTask(TaskAssignmentDto taskAssignmentDto);

    TaskAssignmentDto getTaskAssignmentById(Long id);

    List<TaskAssignmentDto> getAllTaskAssignments();

    // change the code: update task assignment method
    TaskAssignmentDto updateTaskAssignment(Long id, TaskAssignmentDto taskAssignmentDto);

    void deleteTaskAssignment(Long id);
}
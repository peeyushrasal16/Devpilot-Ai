package com.devpilot.ai.services;

import java.util.List;

import com.devpilot.ai.dtos.TaskAssignmentDto;

public interface TaskAssignmentService {

    TaskAssignmentDto assignTask(TaskAssignmentDto taskAssignmentDto);

    TaskAssignmentDto getTaskAssignmentById(Long id);

    List<TaskAssignmentDto> getAllTaskAssignments();

    void deleteTaskAssignment(Long id);
}
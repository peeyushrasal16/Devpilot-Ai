package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.TaskDto;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);

    TaskDto updateTask(Long id, TaskDto taskDto);

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);
}
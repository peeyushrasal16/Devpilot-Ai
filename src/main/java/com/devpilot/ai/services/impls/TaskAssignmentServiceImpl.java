package com.devpilot.ai.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.TaskAssignmentDto;
import com.devpilot.ai.entities.TaskAssignment;
import com.devpilot.ai.repositories.TaskAssignmentRepository;
import com.devpilot.ai.repositories.TaskRepository;
import com.devpilot.ai.repositories.UserRepository;
import com.devpilot.ai.services.TaskAssignmentService;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskAssignmentDto assignTask(TaskAssignmentDto taskAssignmentDto) {

        TaskAssignment taskAssignment =
                modelMapper.map(taskAssignmentDto, TaskAssignment.class);

        taskAssignment.setUser(
                userRepository.findById(taskAssignmentDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        taskAssignment.setTask(
                taskRepository.findById(taskAssignmentDto.getTaskId())
                        .orElseThrow(() -> new RuntimeException("Task not found"))
        );

        taskAssignment.setAssignedAt(LocalDateTime.now());
        taskAssignment.setUpdatedAt(LocalDateTime.now());

        TaskAssignment savedTaskAssignment =
                taskAssignmentRepository.save(taskAssignment);

        TaskAssignmentDto response =
                modelMapper.map(savedTaskAssignment, TaskAssignmentDto.class);

        // change the code: ensure userId, userName, and taskId are set on response
        response.setUserId(savedTaskAssignment.getUser().getId());
        response.setUserName(savedTaskAssignment.getUser().getName());
        response.setTaskId(savedTaskAssignment.getTask().getId());

        return response;
    }
    @Override
    public TaskAssignmentDto getTaskAssignmentById(Long id) {

        TaskAssignment taskAssignment = taskAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        TaskAssignmentDto response = modelMapper.map(taskAssignment, TaskAssignmentDto.class);

        // change the code: ensure userId, userName, and taskId are explicitly set
        if (taskAssignment.getUser() != null) {
            response.setUserId(taskAssignment.getUser().getId());
            response.setUserName(taskAssignment.getUser().getName());
        }
        if (taskAssignment.getTask() != null) {
            response.setTaskId(taskAssignment.getTask().getId());
        }

        return response;
    }

    @Override
    public List<TaskAssignmentDto> getAllTaskAssignments() {

        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findAll();

        return taskAssignments.stream()
                .map(taskAssignment -> {
                    TaskAssignmentDto dto = modelMapper.map(taskAssignment, TaskAssignmentDto.class);
                    // change the code: ensure userId, userName, and taskId are explicitly set
                    if (taskAssignment.getUser() != null) {
                        dto.setUserId(taskAssignment.getUser().getId());
                        dto.setUserName(taskAssignment.getUser().getName());
                    }
                    if (taskAssignment.getTask() != null) {
                        dto.setTaskId(taskAssignment.getTask().getId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // change the code: allow updating task assignment (e.g. reassigning developer to task)
    @Override
    public TaskAssignmentDto updateTaskAssignment(Long id, TaskAssignmentDto taskAssignmentDto) {
        TaskAssignment taskAssignment = taskAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        if (taskAssignmentDto.getUserId() != null) {
            taskAssignment.setUser(
                    userRepository.findById(taskAssignmentDto.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"))
            );
        }

        if (taskAssignmentDto.getTaskId() != null) {
            taskAssignment.setTask(
                    taskRepository.findById(taskAssignmentDto.getTaskId())
                            .orElseThrow(() -> new RuntimeException("Task not found"))
            );
        }

        taskAssignment.setUpdatedAt(LocalDateTime.now());

        TaskAssignment updated = taskAssignmentRepository.save(taskAssignment);

        TaskAssignmentDto response = modelMapper.map(updated, TaskAssignmentDto.class);
        // change the code: ensure userId, userName, and taskId are set on response
        if (updated.getUser() != null) {
            response.setUserId(updated.getUser().getId());
            response.setUserName(updated.getUser().getName());
        }
        if (updated.getTask() != null) {
            response.setTaskId(updated.getTask().getId());
        }
        return response;
    }

    @Override
    public void deleteTaskAssignment(Long id) {

        TaskAssignment taskAssignment = taskAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        taskAssignmentRepository.delete(taskAssignment);
    }
}
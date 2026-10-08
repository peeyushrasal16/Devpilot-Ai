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
import com.devpilot.ai.services.TaskAssignmentService;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskAssignmentDto assignTask(TaskAssignmentDto taskAssignmentDto) {

        TaskAssignment taskAssignment = modelMapper.map(taskAssignmentDto, TaskAssignment.class);
        
        taskAssignment.setAssignedAt(LocalDateTime.now());
        taskAssignment.setUpdatedAt(LocalDateTime.now());

        TaskAssignment savedTaskAssignment = taskAssignmentRepository.save(taskAssignment);

        return modelMapper.map(savedTaskAssignment, TaskAssignmentDto.class);
    }

    @Override
    public TaskAssignmentDto getTaskAssignmentById(Long id) {

        TaskAssignment taskAssignment = taskAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        return modelMapper.map(taskAssignment, TaskAssignmentDto.class);
    }

    @Override
    public List<TaskAssignmentDto> getAllTaskAssignments() {

        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findAll();

        return taskAssignments.stream()
                .map(taskAssignment -> modelMapper.map(taskAssignment, TaskAssignmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTaskAssignment(Long id) {

        TaskAssignment taskAssignment = taskAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task assignment not found"));

        taskAssignmentRepository.delete(taskAssignment);
    }
}
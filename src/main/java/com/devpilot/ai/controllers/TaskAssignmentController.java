package com.devpilot.ai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpilot.ai.dtos.TaskAssignmentDto;
import com.devpilot.ai.services.TaskAssignmentService;

@RestController
@RequestMapping("/task-assignments")
public class TaskAssignmentController {

    @Autowired
    private TaskAssignmentService taskAssignmentService;

    @PostMapping
    public ResponseEntity<TaskAssignmentDto> assignTask(
            @RequestBody TaskAssignmentDto taskAssignmentDto) {

        TaskAssignmentDto savedTaskAssignment =
                taskAssignmentService.assignTask(taskAssignmentDto);

        return new ResponseEntity<TaskAssignmentDto>(
                savedTaskAssignment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskAssignmentDto> getTaskAssignmentById(
            @PathVariable Long id) {

        TaskAssignmentDto taskAssignment =
                taskAssignmentService.getTaskAssignmentById(id);

        return ResponseEntity.ok(taskAssignment);
    }

    // change the code: add PUT endpoint to update task assignment
    @PutMapping("/{id}")
    public ResponseEntity<TaskAssignmentDto> updateTaskAssignment(
            @PathVariable Long id,
            @RequestBody TaskAssignmentDto taskAssignmentDto) {

        TaskAssignmentDto updated = taskAssignmentService.updateTaskAssignment(id, taskAssignmentDto);

        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<TaskAssignmentDto>> getAllTaskAssignments() {

        List<TaskAssignmentDto> taskAssignments =
                taskAssignmentService.getAllTaskAssignments();

        return ResponseEntity.ok(taskAssignments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskAssignment(
            @PathVariable Long id) {

        taskAssignmentService.deleteTaskAssignment(id);

        return ResponseEntity.noContent().build();
    }
}
package com.devpilot.ai.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.devpilot.ai.enums.TaskPriority;
import com.devpilot.ai.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private Long projectId;

    // Added: Project Name for direct frontend display
    private String projectName;
    
 // Assigned users' information
    private List<TaskAssignmentDto> taskAssignments;

}
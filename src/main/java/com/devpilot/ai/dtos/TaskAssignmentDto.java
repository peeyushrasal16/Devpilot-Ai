package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignmentDto {

    private Long id;

    private Long userId;

    private Long taskId;

    private LocalDateTime assignedAt;

    private LocalDateTime updatedAt;
}


package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import com.devpilot.ai.enums.BugPriority;
import com.devpilot.ai.enums.BugStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugDto {

    private Long id;

    private String title;

    private String description;

    private BugStatus status;

    private BugPriority priority;

    private String stepsToReproduce;

    private String expectedResult;

    private String actualResult;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long projectId;
}


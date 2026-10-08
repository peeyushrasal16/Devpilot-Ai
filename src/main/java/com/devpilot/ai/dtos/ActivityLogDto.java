package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogDto {

    private Long id;

    private String action;

    private String description;

    private LocalDateTime createdAt;

    private Long projectId;

    private Long userId;
}


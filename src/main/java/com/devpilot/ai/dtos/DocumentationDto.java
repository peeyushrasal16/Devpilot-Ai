package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import com.devpilot.ai.enums.DocumentationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentationDto {

    private Long id;

    private String title;

    private String content;

    private DocumentationType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long projectId;
}


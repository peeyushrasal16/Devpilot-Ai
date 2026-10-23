package com.devpilot.ai.dtos;

import java.time.LocalDateTime;

import com.devpilot.ai.enums.ProjectMemberRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDto {

    private Long id;

    private Long userId;
    
    // User Name for display
    private String userName;

    private Long projectId;

    // Added: Project Name for direct frontend display
    private String projectName;

    private ProjectMemberRole role;

    private LocalDateTime joinedAt;
}
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

    private Long projectId;

    private ProjectMemberRole role;

    private LocalDateTime joinedAt;
}
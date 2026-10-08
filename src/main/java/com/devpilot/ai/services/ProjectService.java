package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.ProjectDto;
import com.devpilot.ai.projections.ProjectProjection;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(Long id, ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    List<ProjectDto> getAllProjects();

    void deleteProject(Long id);
    
    List<ProjectProjection> getAllProjectsProjection();
}
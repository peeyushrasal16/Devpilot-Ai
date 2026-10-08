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

import com.devpilot.ai.dtos.ProjectDto;
import com.devpilot.ai.projections.ProjectProjection;
import com.devpilot.ai.services.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {

        ProjectDto savedProject = projectService.createProject(projectDto);

        return new ResponseEntity<ProjectDto>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectDto projectDto) {

        ProjectDto updatedProject = projectService.updateProject(id, projectDto);

        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {

        ProjectDto project = projectService.getProjectById(id);

        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {

        List<ProjectDto> projects = projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/projection")
    public ResponseEntity<List<ProjectProjection>> getAllProjectProjection(){
    	return ResponseEntity.ok(projectService.getAllProjectsProjection());
    }
}
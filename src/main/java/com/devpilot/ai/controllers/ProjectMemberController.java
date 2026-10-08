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

import com.devpilot.ai.dtos.ProjectMemberDto;
import com.devpilot.ai.services.ProjectMemberService;

@RestController
@RequestMapping("/project-members")
public class ProjectMemberController {

    @Autowired
    private ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ProjectMemberDto> addProjectMember(
            @RequestBody ProjectMemberDto projectMemberDto) {

        ProjectMemberDto savedProjectMember =
                projectMemberService.addProjectMember(projectMemberDto);

        return new ResponseEntity<ProjectMemberDto>(
                savedProjectMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectMemberDto> updateProjectMember(
            @PathVariable Long id,
            @RequestBody ProjectMemberDto projectMemberDto) {

        ProjectMemberDto updatedProjectMember =
                projectMemberService.updateProjectMember(id, projectMemberDto);

        return ResponseEntity.ok(updatedProjectMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectMemberDto> getProjectMemberById(
            @PathVariable Long id) {

        ProjectMemberDto projectMember =
                projectMemberService.getProjectMemberById(id);

        return ResponseEntity.ok(projectMember);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberDto>> getAllProjectMembers() {

        List<ProjectMemberDto> projectMembers =
                projectMemberService.getAllProjectMembers();

        return ResponseEntity.ok(projectMembers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProjectMember(
            @PathVariable Long id) {

        projectMemberService.removeProjectMember(id);

        return ResponseEntity.noContent().build();
    }
}
package com.devpilot.ai.services;

import java.util.List;
import com.devpilot.ai.dtos.ProjectMemberDto;

public interface ProjectMemberService {

    ProjectMemberDto addProjectMember(ProjectMemberDto projectMemberDto);

    ProjectMemberDto updateProjectMember(Long id, ProjectMemberDto projectMemberDto);

    ProjectMemberDto getProjectMemberById(Long id);

    List<ProjectMemberDto> getAllProjectMembers();

    void removeProjectMember(Long id);
}
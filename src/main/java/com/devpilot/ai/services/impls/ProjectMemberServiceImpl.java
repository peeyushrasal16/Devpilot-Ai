package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.ProjectMemberDto;
import com.devpilot.ai.entities.ProjectMember;
import com.devpilot.ai.repositories.ProjectMemberRepository;
import com.devpilot.ai.repositories.ProjectRepository;
import com.devpilot.ai.repositories.UserRepository;
import com.devpilot.ai.services.ProjectMemberService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // change the code: inject taskAssignmentRepository to delete assigned tasks when member is removed
    @Autowired
    private com.devpilot.ai.repositories.TaskAssignmentRepository taskAssignmentRepository;

    @Override
    public ProjectMemberDto addProjectMember(ProjectMemberDto projectMemberDto) {

        // change the code: prevent duplicate member in the same project
        if (projectMemberRepository.existsByUser_IdAndProject_Id(projectMemberDto.getUserId(), projectMemberDto.getProjectId())) {
            throw new RuntimeException("This user is already a member of this project.");
        }

        ProjectMember projectMember = modelMapper.map(projectMemberDto, ProjectMember.class);

        projectMember.setUser(
                userRepository.findById(projectMemberDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        // Fixed: Use getProjectId() instead of getUserId()
        projectMember.setProject(
                projectRepository.findById(projectMemberDto.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );

        projectMember.setJoinedAt(java.time.LocalDateTime.now());

        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        ProjectMemberDto response =
                modelMapper.map(savedProjectMember, ProjectMemberDto.class);

        // Added: Map user name and project name for display
        if (savedProjectMember.getUser() != null) {
            response.setUserName(savedProjectMember.getUser().getName());
        }
        if (savedProjectMember.getProject() != null) {
            response.setProjectName(savedProjectMember.getProject().getName());
        }

        return response;
        
    }

    @Override
    public ProjectMemberDto updateProjectMember(Long id, ProjectMemberDto projectMemberDto) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        // change the code: allow updating role, project, and user if provided with duplicate validation
        Long targetUserId = projectMemberDto.getUserId() != null ? projectMemberDto.getUserId() : (projectMember.getUser() != null ? projectMember.getUser().getId() : null);
        Long targetProjectId = projectMemberDto.getProjectId() != null ? projectMemberDto.getProjectId() : (projectMember.getProject() != null ? projectMember.getProject().getId() : null);

        if (targetUserId != null && targetProjectId != null && projectMemberRepository.existsByUser_IdAndProject_IdAndIdNot(targetUserId, targetProjectId, id)) {
            throw new RuntimeException("This user is already a member of this project.");
        }

        projectMember.setRole(projectMemberDto.getRole());

        if (projectMemberDto.getUserId() != null) {
            projectMember.setUser(
                    userRepository.findById(projectMemberDto.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"))
            );
        }

        if (projectMemberDto.getProjectId() != null) {
            projectMember.setProject(
                    projectRepository.findById(projectMemberDto.getProjectId())
                            .orElseThrow(() -> new RuntimeException("Project not found"))
            );
        }

        ProjectMember updatedProjectMember = projectMemberRepository.save(projectMember);

        ProjectMemberDto response = modelMapper.map(updatedProjectMember, ProjectMemberDto.class);

        // Added: Map user name and project name for display
        if (updatedProjectMember.getUser() != null) {
            response.setUserName(updatedProjectMember.getUser().getName());
        }
        if (updatedProjectMember.getProject() != null) {
            response.setProjectName(updatedProjectMember.getProject().getName());
        }

        return response;
    }

    @Override
    public ProjectMemberDto getProjectMemberById(Long id) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        ProjectMemberDto response = modelMapper.map(projectMember, ProjectMemberDto.class);

        // Added: Map user name and project name for display
        if (projectMember.getUser() != null) {
            response.setUserName(projectMember.getUser().getName());
        }
        if (projectMember.getProject() != null) {
            response.setProjectName(projectMember.getProject().getName());
        }

        return response;
    }

    @Override
    public List<ProjectMemberDto> getAllProjectMembers() {

        List<ProjectMember> projectMembers = projectMemberRepository.findAll();

        return projectMembers.stream()
                .map(projectMember -> {
                    ProjectMemberDto dto = modelMapper.map(projectMember, ProjectMemberDto.class);
                    // Added: Populate userName and projectName
                    if (projectMember.getUser() != null) {
                        dto.setUserName(projectMember.getUser().getName());
                    }
                    if (projectMember.getProject() != null) {
                        dto.setProjectName(projectMember.getProject().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void removeProjectMember(Long id) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        // change the code: automatically delete all task assignments for this member in this project
        if (projectMember.getUser() != null && projectMember.getProject() != null) {
            taskAssignmentRepository.deleteByUser_IdAndTask_Project_Id(
                    projectMember.getUser().getId(),
                    projectMember.getProject().getId()
            );
        }

        projectMemberRepository.delete(projectMember);
    }
}
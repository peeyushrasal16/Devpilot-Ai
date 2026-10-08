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

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectMemberDto addProjectMember(ProjectMemberDto projectMemberDto) {

        ProjectMember projectMember = modelMapper.map(projectMemberDto, ProjectMember.class);

        projectMember.setUser(
                userRepository.findById(projectMemberDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        projectMember.setProject(
                projectRepository.findById(projectMemberDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );

        projectMember.setJoinedAt(java.time.LocalDateTime.now());

        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        return modelMapper.map(savedProjectMember, ProjectMemberDto.class);
    }

    @Override
    public ProjectMemberDto updateProjectMember(Long id, ProjectMemberDto projectMemberDto) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        projectMember.setRole(projectMemberDto.getRole());

        ProjectMember updatedProjectMember = projectMemberRepository.save(projectMember);

        return modelMapper.map(updatedProjectMember, ProjectMemberDto.class);
    }

    @Override
    public ProjectMemberDto getProjectMemberById(Long id) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        return modelMapper.map(projectMember, ProjectMemberDto.class);
    }

    @Override
    public List<ProjectMemberDto> getAllProjectMembers() {

        List<ProjectMember> projectMembers = projectMemberRepository.findAll();

        return projectMembers.stream()
                .map(projectMember -> modelMapper.map(projectMember, ProjectMemberDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeProjectMember(Long id) {

        ProjectMember projectMember = projectMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        projectMemberRepository.delete(projectMember);
    }
}
package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.ProjectDto;
import com.devpilot.ai.entities.Project;
import com.devpilot.ai.projections.ProjectProjection;
import com.devpilot.ai.repositories.ProjectRepository;
import com.devpilot.ai.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {

        Project project = modelMapper.map(projectDto, Project.class);
        
        project.setCreatedAt(java.time.LocalDateTime.now());
        project.setUpdatedAt(java.time.LocalDateTime.now());

        Project savedProject = projectRepository.save(project);

        return modelMapper.map(savedProject, ProjectDto.class);
    }

    @Override
    public ProjectDto updateProject(Long id, ProjectDto projectDto) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStatus(projectDto.getStatus());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());

        Project updatedProject = projectRepository.save(project);

        return modelMapper.map(updatedProject, ProjectDto.class);
    }

    @Override
    public ProjectDto getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectRepository.delete(project);
    }

	@Override
	public List<ProjectProjection> getAllProjectsProjection() {
		// TODO Auto-generated method stub
		return projectRepository.findAllProjectedBy();
	}
}
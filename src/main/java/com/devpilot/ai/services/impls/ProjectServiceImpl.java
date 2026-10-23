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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    // change the code: inject repositories to cascade delete related records when project is deleted
    @Autowired
    private com.devpilot.ai.repositories.ProjectMemberRepository projectMemberRepository;

    @Autowired
    private com.devpilot.ai.repositories.TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private com.devpilot.ai.repositories.TaskRepository taskRepository;

    @Autowired
    private com.devpilot.ai.repositories.BugRepository bugRepository;

    @Autowired
    private com.devpilot.ai.repositories.DocumentationRepository documentationRepository;

    @Autowired
    private com.devpilot.ai.repositories.ActivityLogRepository activityLogRepository;

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

        // change the code: cascade delete all child entities to avoid foreign key constraint violations
        // 1. Delete task assignments for all tasks belonging to this project
        List<com.devpilot.ai.entities.Task> projectTasks = taskRepository.findByProject_Id(id);
        if (projectTasks != null && !projectTasks.isEmpty()) {
            for (com.devpilot.ai.entities.Task t : projectTasks) {
                taskAssignmentRepository.deleteByTask_Id(t.getId());
            }
        }

        // 2. Delete tasks of this project
        taskRepository.deleteByProject_Id(id);

        // 3. Delete project members
        projectMemberRepository.deleteByProject_Id(id);

        // 4. Delete bugs
        bugRepository.deleteByProject_Id(id);

        // 5. Delete documentation
        documentationRepository.deleteByProject_Id(id);

        // 6. Delete activity logs
        activityLogRepository.deleteByProject_Id(id);

        // 7. Finally delete the project
        projectRepository.delete(project);
    }

	@Override
	public List<ProjectProjection> getAllProjectsProjection() {
		// TODO Auto-generated method stub
		return projectRepository.findAllProjectedBy();
	}
}

package com.devpilot.ai.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpilot.ai.dtos.TaskAssignmentDto;
import com.devpilot.ai.dtos.TaskDto;
import com.devpilot.ai.entities.Task;
import com.devpilot.ai.entities.TaskAssignment;
import com.devpilot.ai.repositories.ProjectRepository;
import com.devpilot.ai.repositories.TaskAssignmentRepository;
import com.devpilot.ai.repositories.TaskRepository;
import com.devpilot.ai.services.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public TaskDto createTask(TaskDto taskDto) {

        Task task = modelMapper.map(taskDto, Task.class);

        if (taskDto.getProjectId() != null) {
            task.setProject(
                projectRepository.findById(taskDto.getProjectId())
                    .orElseThrow(() ->
                        new RuntimeException("Project not found"))
            );
        }

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        return convertToDto(savedTask);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        if (taskDto.getProjectId() != null) {
            task.setProject(
                projectRepository.findById(taskDto.getProjectId())
                    .orElseThrow(() ->
                        new RuntimeException("Project not found"))
            );
        }

        Task updatedTask = taskRepository.save(task);

        return convertToDto(updatedTask);
    }

    @Override
    public TaskDto getTaskById(Long id) {

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        return convertToDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private TaskDto convertToDto(Task task) {

        TaskDto dto = modelMapper.map(task, TaskDto.class);

        if (task.getProject() != null) {
            dto.setProjectName(task.getProject().getName());
            dto.setProjectId(task.getProject().getId());
        }

        List<TaskAssignmentDto> assignments =
            taskAssignmentRepository.findAll().stream()
                .filter(assignment ->
                    assignment.getTask() != null
                    && assignment.getTask().getId().equals(task.getId()))
                .map(this::convertAssignmentToDto)
                .collect(Collectors.toList());

        dto.setTaskAssignments(assignments);

        return dto;
    }

    private TaskAssignmentDto convertAssignmentToDto(
            TaskAssignment assignment) {

        TaskAssignmentDto dto = new TaskAssignmentDto();

        dto.setId(assignment.getId());
        dto.setTaskId(assignment.getTask().getId());
        dto.setAssignedAt(assignment.getAssignedAt());
        dto.setUpdatedAt(assignment.getUpdatedAt());

        if (assignment.getUser() != null) {
            dto.setUserId(assignment.getUser().getId());
            dto.setUserName(assignment.getUser().getName());
        }

        return dto;
    }

    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        taskAssignmentRepository.deleteByTask_Id(id);

        taskRepository.delete(task);
    }
}

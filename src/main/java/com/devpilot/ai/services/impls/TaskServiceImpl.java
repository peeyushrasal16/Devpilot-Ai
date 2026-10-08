package com.devpilot.ai.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devpilot.ai.dtos.TaskDto;
import com.devpilot.ai.entities.Task;
import com.devpilot.ai.repositories.TaskRepository;
import com.devpilot.ai.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TaskDto createTask(TaskDto taskDto) {
		
		Task task = modelMapper.map(taskDto, Task.class);
		
		task.setCreatedAt(java.time.LocalDateTime.now());
		task.setUpdatedAt(java.time.LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, TaskDto.class);
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

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
	}

	
	@Override
	public TaskDto getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return modelMapper.map(task, TaskDto.class);
	}

	
	@Override
	public List<TaskDto> getAllTasks() {
		List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
	}

	
	@Override
	public void deleteTask(Long id) {
		
		 Task task = taskRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Task not found"));

	        taskRepository.delete(task);	
	}

}

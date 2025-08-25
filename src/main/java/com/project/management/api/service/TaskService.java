package com.project.management.api.service;

import com.project.management.api.dto.CreateTaskRequest;
import com.project.management.api.dto.CreateTaskResponse;
import com.project.management.api.dto.TaskResponse;
import com.project.management.api.entity.Task;
import com.project.management.api.enums.StatusEnum;
import com.project.management.api.exception.DataNotFoundException;
import com.project.management.api.repository.EmployeeRepository;
import com.project.management.api.repository.ProjectRepository;
import com.project.management.api.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository projectRepository, ProjectRepository projectRepository1, EmployeeRepository employeeRepository) {
        this.taskRepository = projectRepository;
        this.projectRepository = projectRepository1;
        this.employeeRepository = employeeRepository;
    }

    public Page<TaskResponse> getTasks(Long projectId, LocalDate startDate, LocalDate endDate, PageRequest pageRequest) {
        var result = taskRepository.findAllTaskByProject(projectId, startDate, endDate, pageRequest);

        return result.map(task -> TaskResponse.builder()
                .id(task.getId())
                .projectName(task.getProject().getProjectName())
                .projectStartDate(task.getProject().getStartDate())
                .projectEndDate(task.getProject().getEndDate())
                .name(task.getTaskName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assignedTo(task.getAssignedTo().getName())
                .build()
        );
    }

    public CreateTaskResponse createTask(CreateTaskRequest request) {
        var project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new DataNotFoundException("Project not found"));

        var assignee = employeeRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new DataNotFoundException("Assignee not found"));

        Task savedTask = taskRepository.save(Task.builder()
                .taskName(request.getTaskName())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .status(StatusEnum.PENDING.name())
                .project(project)
                .assignedTo(assignee)
                .build());

        return CreateTaskResponse.builder()
                .id(savedTask.getId())
                .name(savedTask.getTaskName())
                .description(savedTask.getDescription())
                .priority(savedTask.getPriority())
                .dueDate(savedTask.getDueDate())
                .status(savedTask.getStatus())
                .projectName(project.getProjectName())
                .assignedTo(assignee.getName())
                .build();
    }
}

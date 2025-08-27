package com.project.management.api.service;

import com.project.management.api.dto.*;
import com.project.management.api.entity.Task;
import com.project.management.api.enums.StatusEnum;
import com.project.management.api.exception.DataNotFoundException;
import com.project.management.api.repository.EmployeeRepository;
import com.project.management.api.repository.ProjectRepository;
import com.project.management.api.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TaskService(TaskRepository projectRepository, ProjectRepository projectRepository1, EmployeeRepository employeeRepository, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = projectRepository;
        this.projectRepository = projectRepository1;
        this.employeeRepository = employeeRepository;
        this.eventPublisher = eventPublisher;
    }

    public Page<TaskResponse> getTasks(Long projectId, LocalDate startDate, LocalDate endDate, PageRequest pageRequest) {
        var result = taskRepository.findAllTaskByProject(projectId, startDate, endDate, pageRequest);

        return result.map(task -> TaskResponse.builder()
                .id(task.getId())
                .projectName(task.getProject().getProjectName())
                .name(task.getTaskName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assignedTo(task.getAssignedTo().getName())
                .build()
        );
    }

    @Transactional
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
        eventPublisher.publishEvent(new SendTaskNotificationDTO(savedTask));

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

    @Transactional
    public CreateTaskResponse updateTask(UpdateTaskRequest request, Long taskId) {
        var existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new DataNotFoundException("Task not found with id: " + taskId));

        Optional.ofNullable(request.getTaskName()).ifPresent(existingTask::setTaskName);
        Optional.ofNullable(request.getDescription()).ifPresent(existingTask::setDescription);
        Optional.ofNullable(request.getDueDate()).ifPresent(existingTask::setDueDate);
        Optional.ofNullable(request.getPriority()).ifPresent(existingTask::setPriority);
        Optional.ofNullable(request.getStatus()).ifPresent(statusEnum -> existingTask.setStatus(statusEnum.name()));

        Optional.ofNullable(request.getAssignedToId()).ifPresent(assigneeId -> {
            var assignee = employeeRepository.findById(assigneeId)
                    .orElseThrow(() -> new DataNotFoundException("Assignee not found with id: " + assigneeId));
            existingTask.setAssignedTo(assignee);
        });

        var updatedTask = taskRepository.save(existingTask);
        eventPublisher.publishEvent(new SendTaskNotificationDTO(updatedTask));

        return CreateTaskResponse.builder()
                .id(updatedTask.getId())
                .name(updatedTask.getTaskName())
                .description(updatedTask.getDescription())
                .priority(updatedTask.getPriority())
                .dueDate(updatedTask.getDueDate())
                .status(updatedTask.getStatus())
                .projectName(updatedTask.getProject().getProjectName())
                .assignedTo(updatedTask.getAssignedTo().getName())
                .build();
    }
}

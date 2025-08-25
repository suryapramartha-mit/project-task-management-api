package com.project.management.api.service;

import com.project.management.api.dto.TaskResponse;
import com.project.management.api.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {
    private final TaskRepository projectRepository;

    public TaskService(TaskRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Page<TaskResponse> getTasksByProjectId(Long projectId, LocalDate startDate, LocalDate endDate, PageRequest pageRequest) {
        var result = projectRepository.findAllTaskByProject(projectId, startDate, endDate, pageRequest);

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
}

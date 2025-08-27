package com.project.management.api.controller;

import com.project.management.api.dto.*;
import com.project.management.api.service.TaskService;
import com.project.management.api.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get tasks with various filters")
    @Tag(name = "Task API")
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> getTasks(@Valid TaskFilterDTO filter) {
        var direction = Sort.Direction.valueOf(filter.getSort().name());
        var result = taskService.getTasks(filter.getProjectId(), filter.getStartDate(), filter.getEndDate(),
                PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getSortBy().getField()));
        return ResponseEntity.ok(ApiResponse.success(result, "Success"));
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "Create new Task")
    @Tag(name = "Task API")
    public ResponseEntity<ApiResponse<CreateTaskResponse>> createTask(@RequestBody @Valid CreateTaskRequest request) {
        var newTask = taskService.createTask(request);
        return ResponseEntity.ok(ApiResponse.success(newTask, "Task created successfully"));
    }
    @PutMapping(value = "/{taskId}", produces = "application/json")
    @Operation(summary = "Update Task")
    @Tag(name = "Task API")
    public ResponseEntity<ApiResponse<CreateTaskResponse>> updateTask(@PathVariable Long taskId,
            @RequestBody @Valid UpdateTaskRequest request) {
        var updatedTask = taskService.updateTask(request, taskId);
        return ResponseEntity.ok(ApiResponse.success(updatedTask, "Task updated successfully"));
    }
}

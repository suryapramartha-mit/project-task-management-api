package com.project.management.api.controller;

import com.project.management.api.dto.TaskResponse;
import com.project.management.api.enums.TaskSortEnum;
import com.project.management.api.service.TaskService;
import com.project.management.api.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/task")
@Validated
public class TaskController {

    private final TaskService projectService;

    public TaskController(TaskService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get all tasks by project with optional date filters")
    @Tag(name = "Task API")
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> getTasks(@RequestParam(required = false) Long projectId,
                                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "PRIORITY") TaskSortEnum sortBy) {
        var result = projectService.getTasksByProjectId(projectId,startDate, endDate, PageRequest.of(page, size, Sort.Direction.ASC, sortBy.getField()));
        return ResponseEntity.ok(ApiResponse.success(result, "Success"));
    }
}

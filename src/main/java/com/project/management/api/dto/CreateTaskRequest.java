package com.project.management.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class CreateTaskRequest {

    @NotNull
    private Long projectId;

    @NotBlank
    private String taskName;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must not exceed 5")
    private Integer priority;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Long assignedToId;
}

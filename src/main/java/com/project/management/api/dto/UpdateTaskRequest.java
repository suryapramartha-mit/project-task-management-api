package com.project.management.api.dto;

import com.project.management.api.enums.StatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateTaskRequest {
    private String taskName;
    private String description;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must not exceed 5")
    private Integer priority;

    private LocalDate dueDate;
    private StatusEnum status;
    private Long assignedToId;
}

package com.project.management.api.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String description;
    private LocalDate dueDate;
    private Integer priority;
    private String status;
    private String assignedTo;
}

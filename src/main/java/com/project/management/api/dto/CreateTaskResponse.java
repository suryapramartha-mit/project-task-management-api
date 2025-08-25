package com.project.management.api.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskResponse {
    private Long id;
    private String name;
    private String projectName;
    private String description;
    private LocalDate dueDate;
    private Integer priority;
    private String status;
    private String assignedTo;
}

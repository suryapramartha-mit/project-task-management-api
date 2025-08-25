package com.project.management.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Column(name = "id", nullable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "priority")
    private Integer priority; // 1 (highest) to 5 (lowest)

    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}

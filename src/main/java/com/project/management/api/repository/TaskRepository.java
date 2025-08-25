package com.project.management.api.repository;

import com.project.management.api.entity.Project;
import com.project.management.api.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Project, Long> {

    @Query("SELECT t FROM Task t " +
            "JOIN t.project p " +
            "WHERE (:startDate IS NULL OR p.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.endDate <= :endDate) " +
            "AND (:projectId IS NULL OR p.id = :projectId)")
    Page<Task> findAllTaskByProject(Long projectId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}

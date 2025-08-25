package com.project.management.api;

import com.project.management.api.dto.TaskResponse;
import com.project.management.api.entity.Employee;
import com.project.management.api.entity.Project;
import com.project.management.api.entity.Task;
import com.project.management.api.repository.TaskRepository;
import com.project.management.api.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testSuccess_getTasks() {
        // Given
        Long projectId = 1L;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Task> page = getData(projectId, startDate, endDate);

        // Mock repository
        when(taskRepository.findAllTaskByProject(projectId, startDate, endDate, pageRequest))
                .thenReturn(page);

        // When
        Page<TaskResponse> result = taskService.getTasksByProjectId(projectId, startDate, endDate, pageRequest);

        // Then
        assertEquals(1, result.getContent().size());
        TaskResponse response = result.getContent().get(0);
        assertEquals(100L, response.getId());
        assertEquals("Test Task", response.getName());
        assertEquals(1, response.getPriority());
        assertEquals("TestName", response.getAssignedTo());
        assertEquals("Test Project", response.getProjectName());

        // Verify repository was called
        verify(taskRepository, times(1)).findAllTaskByProject(projectId, startDate, endDate, pageRequest);
    }

    private static Page<Task> getData(Long projectId, LocalDate startDate, LocalDate endDate) {
        // Create Project
        Project project = new Project();
        project.setId(projectId);
        project.setProjectName("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        // Create Employee
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setName("TestName");

        // Create Task
        Task task = new Task();
        task.setId(100L);
        task.setProject(project);
        task.setTaskName("Test Task");
        task.setDescription("Description");
        task.setDueDate(LocalDate.of(2025, 8, 30));
        task.setPriority(1);
        task.setStatus("COMPLETED");
        task.setAssignedTo(employee);

        Page<Task> page = new PageImpl<>(List.of(task));
        return page;
    }
}

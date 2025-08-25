package com.project.management.api;

import com.project.management.api.dto.CreateTaskRequest;
import com.project.management.api.dto.CreateTaskResponse;
import com.project.management.api.dto.TaskResponse;
import com.project.management.api.entity.Employee;
import com.project.management.api.entity.Project;
import com.project.management.api.entity.Task;
import com.project.management.api.enums.StatusEnum;
import com.project.management.api.exception.DataNotFoundException;
import com.project.management.api.repository.EmployeeRepository;
import com.project.management.api.repository.ProjectRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

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
        // When
        when(taskRepository.findAllTaskByProject(projectId, startDate, endDate, pageRequest))
                .thenReturn(page);

        Page<TaskResponse> result = taskService.getTasks(projectId, startDate, endDate, pageRequest);

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

    @Test
    void testSuccess_CreateTask() {
        // Given
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(1L)
                .taskName("Task A")
                .description("Description")
                .priority(3)
                .dueDate(LocalDate.of(2025, 3, 1))
                .assignedToId(2L)
                .build();

        Project project = Project.builder()
                .id(1L)
                .projectName("Project A")
                .startDate(LocalDate.of(2025, 8, 1))
                .endDate(LocalDate.of(2025, 12, 1))
                .build();

        Employee assignee = Employee.builder()
                .id(2L)
                .name("Test Name")
                .build();

        Task savedTask = Task.builder()
                .id(10L)
                .taskName("Task A")
                .description("Description")
                .priority(3)
                .dueDate(LocalDate.of(2025, 9, 1))
                .status(StatusEnum.PENDING.name())
                .project(project)
                .assignedTo(assignee)
                .build();

        // When
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(assignee));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        CreateTaskResponse response = taskService.createTask(request);

        // Then
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Task A", response.getName());
        assertEquals("Description", response.getDescription());
        assertEquals(3, response.getPriority());
        assertEquals(LocalDate.of(2025, 9, 1), response.getDueDate());
        assertEquals(StatusEnum.PENDING.name(), response.getStatus());
        assertEquals("Project A", response.getProjectName());
        assertEquals("Test Name", response.getAssignedTo());

        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(2L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testError_CreateTask_projectNotFound() {
        // Given
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(99L)
                .assignedToId(2L)
                .taskName("Task A")
                .build();

        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        // Then
        assertThrows(DataNotFoundException.class, () -> taskService.createTask(request));
    }

    @Test
    void testError_CreateTask_assigneeNotFound() {
        // Given
        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(1L)
                .assignedToId(99L)
                .taskName("Task A")
                .build();

        Project project = Project.builder().id(1L).projectName("Project A").build();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        // Then
        assertThrows(DataNotFoundException.class, () -> taskService.createTask(request));
    }
    private static Page<Task> getData(Long projectId, LocalDate startDate, LocalDate endDate) {
        Project project = new Project();
        project.setId(projectId);
        project.setProjectName("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        Employee employee = new Employee();
        employee.setId(10L);
        employee.setName("TestName");

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

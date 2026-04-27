package com.portfolio.taskapi.service.impl;

import com.portfolio.taskapi.dto.request.TaskRequest;
import com.portfolio.taskapi.dto.response.TaskResponse;
import com.portfolio.taskapi.entity.Task;
import com.portfolio.taskapi.exception.ResourceNotFoundException;
import com.portfolio.taskapi.mapper.TaskMapper;
import com.portfolio.taskapi.repository.TaskRepository;
import com.portfolio.taskapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, new TaskMapper());
    }

    @Test
    void shouldCreateTask() {
        TaskRequest request = new TaskRequest(
                "Prepare tests",
                "Cover main service behavior",
                false,
                LocalDate.of(2026, 5, 10)
        );

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            task.setCreatedAt(LocalDateTime.of(2026, 4, 27, 10, 0));
            return task;
        });

        TaskResponse response = taskService.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Prepare tests");
        assertThat(response.description()).isEqualTo("Cover main service behavior");
        assertThat(response.completed()).isFalse();
        assertThat(response.dueDate()).isEqualTo(LocalDate.of(2026, 5, 10));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getTitle()).isEqualTo("Prepare tests");
        assertThat(taskCaptor.getValue().getDescription()).isEqualTo("Cover main service behavior");
    }

    @Test
    void shouldFindTaskById() {
        Task task = task(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.findById(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Prepare portfolio project");
        assertThat(response.description()).isEqualTo("Create the base API for tasks");
        assertThat(response.completed()).isFalse();
        assertThat(response.dueDate()).isEqualTo(LocalDate.of(2026, 5, 10));
    }

    @Test
    void shouldDeleteTask() {
        Task task = task(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotExist() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Task not found with id 99");
    }

    private Task task(Long id) {
        Task task = new Task();
        task.setId(id);
        task.setTitle("Prepare portfolio project");
        task.setDescription("Create the base API for tasks");
        task.setCompleted(false);
        task.setDueDate(LocalDate.of(2026, 5, 10));
        task.setCreatedAt(LocalDateTime.of(2026, 4, 27, 10, 0));
        return task;
    }
}

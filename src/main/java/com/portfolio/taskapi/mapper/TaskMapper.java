package com.portfolio.taskapi.mapper;

import com.portfolio.taskapi.dto.request.TaskRequest;
import com.portfolio.taskapi.dto.response.TaskResponse;
import com.portfolio.taskapi.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request) {
        Task task = new Task();
        applyChanges(task, request);
        return task;
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getDueDate(),
                task.getCreatedAt()
        );
    }

    public void applyChanges(Task task, TaskRequest request) {
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCompleted(request.completed());
        task.setDueDate(request.dueDate());
    }
}

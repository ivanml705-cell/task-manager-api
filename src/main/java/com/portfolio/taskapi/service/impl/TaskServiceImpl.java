package com.portfolio.taskapi.service.impl;

import com.portfolio.taskapi.entity.Task;
import com.portfolio.taskapi.repository.TaskRepository;
import com.portfolio.taskapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}

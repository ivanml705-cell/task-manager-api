package com.portfolio.taskapi.service.impl;

import com.portfolio.taskapi.dto.request.TaskRequest;
import com.portfolio.taskapi.dto.response.TaskResponse;
import com.portfolio.taskapi.entity.Task;
import com.portfolio.taskapi.exception.ResourceNotFoundException;
import com.portfolio.taskapi.mapper.TaskMapper;
import com.portfolio.taskapi.repository.TaskRepository;
import com.portfolio.taskapi.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> findAll() {
        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponse findById(Long id) {
        return taskMapper.toResponse(findTaskById(id));
    }

    @Override
    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = findTaskById(id);
        taskMapper.applyChanges(task, request);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }
}

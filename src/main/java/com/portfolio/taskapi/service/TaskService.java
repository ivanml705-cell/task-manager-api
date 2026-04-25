package com.portfolio.taskapi.service;

import com.portfolio.taskapi.dto.request.TaskRequest;
import com.portfolio.taskapi.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> findAll();

    TaskResponse findById(Long id);

    TaskResponse create(TaskRequest request);

    TaskResponse update(Long id, TaskRequest request);

    void delete(Long id);
}

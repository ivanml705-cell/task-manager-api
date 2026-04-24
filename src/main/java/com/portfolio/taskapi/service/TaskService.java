package com.portfolio.taskapi.service;

import com.portfolio.taskapi.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();
}

package com.portfolio.taskapi.repository;

import com.portfolio.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

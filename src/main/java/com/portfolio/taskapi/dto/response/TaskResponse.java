package com.portfolio.taskapi.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDate dueDate,
        LocalDateTime createdAt
) {
}

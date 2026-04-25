package com.portfolio.taskapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank
        @Size(max = 120)
        String title,

        @Size(max = 500)
        String description,

        @NotNull
        Boolean completed,

        LocalDate dueDate
) {
}

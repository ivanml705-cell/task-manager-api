package com.portfolio.taskapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldPerformCrudOperations() throws Exception {
        String createRequest = """
                {
                  "title": "Prepare portfolio project",
                  "description": "Create the base API for tasks",
                  "completed": false,
                  "dueDate": "2026-05-10"
                }
                """;

        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Prepare portfolio project"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.dueDate").value("2026-05-10"))
                .andReturn();

        JsonNode createdTask = objectMapper.readTree(createResult.getResponse().getContentAsString());
        long taskId = createdTask.get("id").asLong();

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskId))
                .andExpect(jsonPath("$[0].title").value("Prepare portfolio project"));

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.description").value("Create the base API for tasks"));

        String updateRequest = """
                {
                  "title": "Prepare portfolio API",
                  "description": "CRUD for task management",
                  "completed": true,
                  "dueDate": "2026-05-15"
                }
                """;

        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("Prepare portfolio API"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.dueDate").value("2026-05-15"));

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Task not found with id " + taskId))
                .andExpect(jsonPath("$.path").value("/tasks/" + taskId))
                .andExpect(jsonPath("$.errors").isMap())
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test
    void shouldReturnValidationErrorsWhenRequestIsInvalid() throws Exception {
        String invalidRequest = """
                {
                  "title": "",
                  "description": "%s",
                  "completed": null,
                  "dueDate": "2026-05-10"
                }
                """.formatted("a".repeat(501));

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/tasks"))
                .andExpect(jsonPath("$.errors.title").value("Title is required"))
                .andExpect(jsonPath("$.errors.description").value("Description must not exceed 500 characters"))
                .andExpect(jsonPath("$.errors.completed").value("Completed is required"))
                .andExpect(jsonPath("$.timestamp").isString());
    }
}

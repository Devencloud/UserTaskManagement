package com.example.taskmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.TaskRequestDto;
import com.example.taskmanagement.dto.TaskResponseDto;
import com.example.taskmanagement.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);

    }

    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<TaskResponseDto> getTasksByUser() {
        return taskService.getTasksByUser();

    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.updateTask(id, taskRequestDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Deleted successfully";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTaskForUser(@PathVariable Long userId,
            @Valid @RequestBody TaskRequestDto taskRequestDTO) {
        return taskService.createTaskForUser(userId, taskRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userId}")
    public List<TaskResponseDto> getTasksForUser(@PathVariable Long userId) {
        return taskService.getTasksForUser(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userId}/{taskId}")
    public TaskResponseDto getTaskForUser(@PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.getTaskForUser(userId, taskId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}/{taskId}")
    public TaskResponseDto updateTaskForUser(@PathVariable Long userId, @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDto taskRequestDTO) {
        return taskService.updateTaskForUser(userId, taskId, taskRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskForUser(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTaskForUser(userId, taskId);
    }

}












  










 






 
 




































































package com.example.taskmanagement.dto;

import java.time.LocalDateTime;

import com.example.taskmanagement.model.Task;

import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;
   




    
}

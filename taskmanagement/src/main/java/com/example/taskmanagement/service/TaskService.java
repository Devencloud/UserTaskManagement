package com.example.taskmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.TaskRequestDto;
import com.example.taskmanagement.dto.TaskResponseDto;
import com.example.taskmanagement.exception.NotFoundException;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.EntityNotFoundException;


@Service
public class TaskService {
    
     private  TaskRepository taskRepository;

     private UserRepository userRepository;
     public TaskService(TaskRepository taskRepository,UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
     }

    
    

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto){
        Long userId = getCurrentUserId();

        User user  = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found with id: "+userId));
        Task task= new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return mapToResponseDto(savedTask);



    }

    public TaskResponseDto getTaskById(Long id){
        Long userId = getCurrentUserId();
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task not found with id: "+id));
        if(!task.getUser().getId().equals(userId)){
             throw new SecurityException("Unauthorized access to task");
        }
        return mapToResponseDto(task);

    }
    public List<TaskResponseDto> getTasksByUser(){
        Long userId = getCurrentUserId();
        return taskRepository.findByUserId(userId).stream().map(this::mapToResponseDto).toList();

    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto){
        Long userId= getCurrentUserId();
        
         Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
         if(!task.getUser().getId().equals(userId)){
              throw new SecurityException("Unauthorized access to task");
         }
         task.setTitle(taskRequestDto.getTitle());
         task.setDescription(taskRequestDto.getDescription());
         task.setStatus(taskRequestDto.getStatus());
         task.setCreatedAt(LocalDateTime.now());
         Task updatedTask = taskRepository.save(task);
         return mapToResponseDto(updatedTask);
    }


    public void deleteTask(Long id){
        Long userId  = getCurrentUserId();
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task not found with id: "+id));
        if(!task.getUser().getId().equals(userId)){
              throw new SecurityException("Unauthorized access to task");
        }
        taskRepository.delete(task);
        
        
    }
   

    
    public TaskResponseDto mapToResponseDto(Task task){
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUserId(task.getUser().getId());
        return dto;

    }
    private Long getCurrentUserId() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(userId);
    }

    //API points for admins
    public TaskResponseDto createTaskForUser(Long userId, TaskRequestDto taskRequestDTO) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

    Task task = new Task();
    task.setTitle(taskRequestDTO.getTitle());
    task.setDescription(taskRequestDTO.getDescription());
    task.setStatus(taskRequestDTO.getStatus());
    task.setCreatedAt(LocalDateTime.now());
    task.setUser(user);

    Task savedTask = taskRepository.save(task);
    return mapToResponseDto(savedTask);
}
    

     public List<TaskResponseDto> getTasksForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
      public TaskResponseDto getTaskForUser(Long userId, Long taskId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().getId().equals(userId)) {
            throw new SecurityException("Task does not belong to specified user");
        }
        return mapToResponseDto(task);
    }
     public TaskResponseDto updateTaskForUser(Long userId, Long taskId, TaskRequestDto taskRequestDTO) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().getId().equals(userId)) {
            throw new SecurityException("Task does not belong to specified user");
        }
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());
        Task updatedTask = taskRepository.save(task);
        return mapToResponseDto(updatedTask);
    }
     public void deleteTaskForUser(Long userId, Long taskId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().getId().equals(userId)) {
            throw new SecurityException("Task does not belong to specified user");
        }
        taskRepository.delete(task);
    }


    


    
}
































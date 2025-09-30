package com.example.taskmanagement.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.UserRequestDto;
import com.example.taskmanagement.dto.UserResponseDto;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.UserService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private UserService userService ;
    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        logger.info("Creating user with username: {}", userRequestDto.getName());
        return userService.createUser(userRequestDto);
        
    }
    @PostMapping("/bulk")
    public List<UserResponseDto> createUserBulk(@RequestBody List<@Valid UserRequestDto> userRequestDtos){
        logger.info("Creating {} users in bulk", userRequestDtos.size());
        return userRequestDtos.stream().map(this::createUser).toList();
    }
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        logger.info("Fetching user with ID: {}",id);

        return userService.getUserById(id);
    }
    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto ){
        logger.info("Updating user with ID: {}", id);
        return userService.updateUser(id, userRequestDto);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return "Deleted succesfully";
    }




    
}













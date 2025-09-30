package com.example.taskmanagement.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.UserRequestDto;
import com.example.taskmanagement.dto.UserResponseDto;
import com.example.taskmanagement.exception.NotFoundException;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto){
       User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setRole(userRequestDto.getRole());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }
    public List<UserResponseDto> createUsersBulk(List<UserRequestDto> userRequestDtos) {
    return userRequestDtos.stream()
            .map(this::createUser)
            .toList();
}


    public UserResponseDto getUserById(Long id){
        User user  = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id "+id));
        return mapToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public UserResponseDto updateUser(Long id,UserRequestDto userRequestDto){
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id "+id));
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(userRequestDto.getRole());

        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }



    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id "+id));
        userRepository.delete(user);
    }


   private UserResponseDto mapToResponseDto(User user){
      UserResponseDto dto = new UserResponseDto();
      dto.setId(user.getId());
      dto.setName(user.getName());
      dto.setEmail(user.getEmail());
      dto.setRole(user.getRole());
      return dto;
    
   }


    
}

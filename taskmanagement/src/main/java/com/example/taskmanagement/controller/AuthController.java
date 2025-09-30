package com.example.taskmanagement.controller;
import com.example.taskmanagement.dto.LoginRequestDto;
import com.example.taskmanagement.dto.LoginResponseDto;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.security.JwtUtil;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/login")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,JwtUtil jwtUtil,BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtUtil= jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if(!passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword())){
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");

        }
        String token = jwtUtil.generateToken(user.getId().toString(), user.getRole());
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        return response;


    }

    
}




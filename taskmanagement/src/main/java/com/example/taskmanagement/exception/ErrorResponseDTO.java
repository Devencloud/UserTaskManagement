package com.example.taskmanagement.exception;




import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ErrorResponseDTO {
    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private String path;

   
    
    
    
}

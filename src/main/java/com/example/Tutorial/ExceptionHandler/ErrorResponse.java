package com.example.Tutorial.ExceptionHandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final String message;
    private final int statusCode;
    private final HttpStatus status;
    private LocalDateTime localDateTime;

    public ErrorResponse(HttpStatus status, String message, int statusCode) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    public ErrorResponse(HttpStatus status, String message, LocalDateTime localDateTime) {
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.localDateTime = localDateTime;
    }
}
